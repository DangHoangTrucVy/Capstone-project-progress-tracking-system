package com.capstone.tracking.artifact;

import com.capstone.tracking.common.BaseEntity;
import com.capstone.tracking.group.StudentGroup;
import com.capstone.tracking.meeting.MeetingSession;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * blueprint.md §8 Data Model -> ArtifactSubmission entity (Sprint 3 — API-005). Data layer only for now.
 * {@code fileUrl} points at object storage (S3/MinIO) per the DB-scaling notes — this table never stores
 * the file bytes themselves.
 */
@Entity
@Table(name = "artifact_submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtifactSubmission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private StudentGroup group;

    /** Nullable: a group may submit an artifact ahead of the meeting it will be discussed in. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private MeetingSession session;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String fileUrl;

    private String fileType;

    @Column(nullable = false)
    @Builder.Default
    private int version = 1;

    @Column(nullable = false)
    private Instant submittedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ArtifactStatus status = ArtifactStatus.SUBMITTED;
}
