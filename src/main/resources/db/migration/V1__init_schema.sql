-- Sprint 1: Foundation & RBAC schema, per blueprint.md §8 Data Model.
-- Primary keys are UUIDs generated application-side (Hibernate GenerationType.UUID),
-- so no DB-side default/extension is required.

CREATE TABLE users (
    id              UUID PRIMARY KEY,
    email           VARCHAR(255) NOT NULL UNIQUE,
    full_name       VARCHAR(255) NOT NULL,
    password_hash   VARCHAR(255),
    role            VARCHAR(20)  NOT NULL CHECK (role IN ('ADMIN', 'INSTRUCTOR', 'GROUP_LEADER', 'STUDENT')),
    status          VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'SUSPENDED', 'INACTIVE')),
    avatar_url      VARCHAR(500),
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL
);

CREATE INDEX idx_users_role ON users (role);

CREATE TABLE topics (
    id              UUID PRIMARY KEY,
    topic_code      VARCHAR(50)  NOT NULL UNIQUE,
    title           VARCHAR(255) NOT NULL,
    description     TEXT,
    category        VARCHAR(100),
    admin_id        UUID NOT NULL REFERENCES users (id),
    status          VARCHAR(20)  NOT NULL DEFAULT 'DRAFT' CHECK (status IN ('DRAFT', 'PUBLISHED', 'ARCHIVED')),
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL
);

CREATE INDEX idx_topics_status ON topics (status);

CREATE TABLE student_groups (
    id              UUID PRIMARY KEY,
    group_code      VARCHAR(50) NOT NULL UNIQUE,
    topic_id        UUID NOT NULL REFERENCES topics (id),
    supervisor_id   UUID NOT NULL REFERENCES users (id),
    semester        VARCHAR(20) NOT NULL,
    status          VARCHAR(20) NOT NULL DEFAULT 'FORMED' CHECK (status IN ('FORMED', 'ACTIVE', 'COMPLETED', 'ARCHIVED')),
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL
);

CREATE INDEX idx_student_groups_supervisor ON student_groups (supervisor_id);
CREATE INDEX idx_student_groups_topic ON student_groups (topic_id);

CREATE TABLE group_members (
    id              UUID PRIMARY KEY,
    group_id        UUID NOT NULL REFERENCES student_groups (id),
    user_id         UUID NOT NULL REFERENCES users (id),
    is_leader       BOOLEAN NOT NULL DEFAULT FALSE,
    joined_at       TIMESTAMP NOT NULL,
    status          VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'REMOVED')),
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL,
    CONSTRAINT uq_group_members_group_user UNIQUE (group_id, user_id)
);

CREATE INDEX idx_group_members_group ON group_members (group_id);
CREATE INDEX idx_group_members_user ON group_members (user_id);

CREATE TABLE question_bank_items (
    id              UUID PRIMARY KEY,
    topic_id        UUID NOT NULL REFERENCES topics (id),
    category        VARCHAR(100),
    question_text   TEXT NOT NULL,
    guidance_notes  TEXT,
    created_by      UUID NOT NULL REFERENCES users (id),
    status          VARCHAR(20) NOT NULL DEFAULT 'DRAFT' CHECK (status IN ('DRAFT', 'ACTIVE', 'DEPRECATED')),
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL
);

CREATE INDEX idx_question_bank_topic ON question_bank_items (topic_id);
