package com.capstone.tracking.meeting;

import com.capstone.tracking.common.BaseEntity;
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

/** blueprint.md §8 Data Model -> MeetingMinute entity (Sprint 4 — API-008, API-009). Data layer only for now. */
@Entity
@Table(name = "meeting_minutes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingMinute extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false, unique = true)
    private MeetingSession session;

    @Column(columnDefinition = "TEXT")
    private String generatedContent;

    @Column(columnDefinition = "TEXT")
    private String finalContent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private MinuteStatus status = MinuteStatus.DRAFT;

    private Instant studentSignedAt;

    private Instant instructorSignedAt;
}
