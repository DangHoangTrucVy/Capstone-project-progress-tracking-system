package com.capstone.tracking.meeting;

import com.capstone.tracking.common.BaseEntity;
import com.capstone.tracking.scheduling.Booking;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * blueprint.md §8 Data Model -> MeetingSession entity (Sprint 4 — API-007, API-008).
 * Data layer only for now: entity + repository, ready for Sprint 4's service/controller
 * (see README "What's implemented" for the current sprint boundary).
 */
@Entity
@Table(name = "meeting_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingSession extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    private Instant startedAt;

    private Instant endedAt;

    @Column(columnDefinition = "TEXT")
    private String rawNotes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private SessionStatus sessionStatus = SessionStatus.SCHEDULED;
}
