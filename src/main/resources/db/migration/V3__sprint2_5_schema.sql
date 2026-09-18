-- Sprint 2-5 schema, per blueprint.md §8 Data Model. Sprint 2 (schedule_slots, bookings) has real
-- service/controller code on top of it; the rest (meeting_sessions onward) is data layer only for now —
-- see README.md for the current sprint boundary.

CREATE TABLE schedule_slots (
    id                  UUID PRIMARY KEY,
    instructor_id       UUID NOT NULL REFERENCES users (id),
    start_time          TIMESTAMP NOT NULL,
    end_time            TIMESTAMP NOT NULL,
    duration_minutes    INT NOT NULL,
    capacity_groups     INT NOT NULL,
    booked_count        INT NOT NULL DEFAULT 0,
    location_type       VARCHAR(20) NOT NULL CHECK (location_type IN ('ONLINE', 'OFFLINE')),
    meeting_url         VARCHAR(500),
    status              VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE'
                         CHECK (status IN ('AVAILABLE', 'FULL', 'IN_SESSION', 'COMPLETED', 'CANCELLED')),
    created_at          TIMESTAMP NOT NULL,
    updated_at          TIMESTAMP NOT NULL,
    CONSTRAINT chk_slot_time_order CHECK (end_time > start_time),
    CONSTRAINT chk_slot_capacity CHECK (booked_count >= 0 AND booked_count <= capacity_groups)
);

-- Serves the exact UC-002 query shape: "slot còn trống theo giảng viên + khoảng ngày + trạng thái".
CREATE INDEX idx_slots_instructor_status_start ON schedule_slots (instructor_id, status, start_time);

CREATE TABLE bookings (
    id                  UUID PRIMARY KEY,
    slot_id             UUID NOT NULL REFERENCES schedule_slots (id),
    group_id            UUID NOT NULL REFERENCES student_groups (id),
    booking_status      VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED'
                         CHECK (booking_status IN ('CONFIRMED', 'ATTENDED', 'CANCELLED', 'NO_SHOW')),
    booked_at           TIMESTAMP NOT NULL,
    notes               TEXT,
    cancelled_at        TIMESTAMP,
    created_at          TIMESTAMP NOT NULL,
    updated_at          TIMESTAMP NOT NULL
);

CREATE INDEX idx_bookings_slot ON bookings (slot_id);
-- Backs BookingRepository.existsByGroupIdAndBookingStatus — the "one active booking per group" check
-- BookingService.book() runs before it ever takes the slot's row lock.
CREATE INDEX idx_bookings_group_status ON bookings (group_id, booking_status);

CREATE TABLE meeting_sessions (
    id                  UUID PRIMARY KEY,
    booking_id          UUID NOT NULL UNIQUE REFERENCES bookings (id),
    started_at          TIMESTAMP,
    ended_at            TIMESTAMP,
    raw_notes           TEXT,
    session_status      VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED'
                         CHECK (session_status IN ('SCHEDULED', 'IN_PROGRESS', 'CONCLUDED')),
    created_at          TIMESTAMP NOT NULL,
    updated_at          TIMESTAMP NOT NULL
);

CREATE TABLE requirement_logs (
    id                  UUID PRIMARY KEY,
    session_id          UUID NOT NULL REFERENCES meeting_sessions (id),
    group_id            UUID NOT NULL REFERENCES student_groups (id),
    title               VARCHAR(255) NOT NULL,
    description         TEXT,
    priority            VARCHAR(10) NOT NULL CHECK (priority IN ('HIGH', 'MEDIUM', 'LOW')),
    status              VARCHAR(20) NOT NULL DEFAULT 'OPEN'
                         CHECK (status IN ('OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED')),
    assigned_to         UUID REFERENCES users (id),
    created_at          TIMESTAMP NOT NULL,
    updated_at          TIMESTAMP NOT NULL
);

CREATE INDEX idx_requirement_logs_session ON requirement_logs (session_id);
CREATE INDEX idx_requirement_logs_group_status ON requirement_logs (group_id, status);

CREATE TABLE meeting_minutes (
    id                      UUID PRIMARY KEY,
    session_id              UUID NOT NULL UNIQUE REFERENCES meeting_sessions (id),
    generated_content       TEXT,
    final_content           TEXT,
    status                  VARCHAR(20) NOT NULL DEFAULT 'DRAFT'
                             CHECK (status IN ('DRAFT', 'UNDER_REVIEW', 'APPROVED', 'REJECTED')),
    student_signed_at       TIMESTAMP,
    instructor_signed_at    TIMESTAMP,
    created_at              TIMESTAMP NOT NULL,
    updated_at              TIMESTAMP NOT NULL
);

CREATE TABLE evaluation_records (
    id                          UUID PRIMARY KEY,
    group_id                    UUID NOT NULL REFERENCES student_groups (id),
    instructor_id                UUID NOT NULL REFERENCES users (id),
    topic_fit_score              INT NOT NULL CHECK (topic_fit_score BETWEEN 0 AND 100),
    product_quality_score        INT NOT NULL CHECK (product_quality_score BETWEEN 0 AND 100),
    communication_score          INT NOT NULL CHECK (communication_score BETWEEN 0 AND 100),
    total_score                  DOUBLE PRECISION NOT NULL,
    feedback_notes                TEXT,
    evaluated_at                  TIMESTAMP,
    status                        VARCHAR(20) NOT NULL DEFAULT 'DRAFT'
                                   CHECK (status IN ('DRAFT', 'SUBMITTED', 'PUBLISHED')),
    created_at                    TIMESTAMP NOT NULL,
    updated_at                    TIMESTAMP NOT NULL
);

CREATE INDEX idx_evaluation_records_group ON evaluation_records (group_id);

CREATE TABLE artifact_submissions (
    id                  UUID PRIMARY KEY,
    group_id            UUID NOT NULL REFERENCES student_groups (id),
    session_id          UUID REFERENCES meeting_sessions (id),
    title               VARCHAR(255) NOT NULL,
    file_url            VARCHAR(1000) NOT NULL,
    file_type           VARCHAR(50),
    version             INT NOT NULL DEFAULT 1,
    submitted_at        TIMESTAMP NOT NULL,
    status              VARCHAR(20) NOT NULL DEFAULT 'SUBMITTED'
                         CHECK (status IN ('SUBMITTED', 'SUPERCEDED', 'ACCEPTED')),
    created_at          TIMESTAMP NOT NULL,
    updated_at          TIMESTAMP NOT NULL
);

CREATE INDEX idx_artifact_submissions_group ON artifact_submissions (group_id);

-- NFR-006 / §11: immutable trail. No updated_at — see SystemAuditTrail's javadoc for why.
CREATE TABLE system_audit_trail (
    id                  UUID PRIMARY KEY,
    entity_name         VARCHAR(100) NOT NULL,
    entity_id           UUID NOT NULL,
    action              VARCHAR(20) NOT NULL CHECK (action IN ('CREATE', 'UPDATE', 'CANCEL', 'APPROVE', 'REJECT', 'SIGN')),
    performed_by        UUID NOT NULL REFERENCES users (id),
    performed_at        TIMESTAMP NOT NULL,
    details_json        TEXT
);

CREATE INDEX idx_audit_entity ON system_audit_trail (entity_name, entity_id);
