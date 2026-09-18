package com.capstone.tracking.evaluation;

import com.capstone.tracking.common.BaseEntity;
import com.capstone.tracking.group.StudentGroup;
import com.capstone.tracking.user.User;
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
 * blueprint.md §8 Data Model -> EvaluationRecord entity (Sprint 5 — API-010). Data layer only for now.
 * Restricted Confidential per §11 — never expose this entity directly through a controller; always go
 * through a DTO the way {@code UserResponse}/{@code TopicResponse} already do in Sprint 1.
 */
@Entity
@Table(name = "evaluation_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private StudentGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    /** 0-100. Weights per A-004 (Unconfirmed as of blueprint v1.0): Topic Fit 40%, Product Quality 40%, Communication 20%. */
    @Column(nullable = false)
    private int topicFitScore;

    @Column(nullable = false)
    private int productQualityScore;

    @Column(nullable = false)
    private int communicationScore;

    @Column(nullable = false)
    private double totalScore;

    @Column(columnDefinition = "TEXT")
    private String feedbackNotes;

    private Instant evaluatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EvaluationStatus status = EvaluationStatus.DRAFT;

    /** A-004's weighting, kept alongside the entity so the formula lives in one place once Sprint 5 wires it up. */
    public static double weightedTotal(int topicFit, int productQuality, int communication) {
        return topicFit * 0.4 + productQuality * 0.4 + communication * 0.2;
    }
}
