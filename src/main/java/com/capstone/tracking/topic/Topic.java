package com.capstone.tracking.topic;

import com.capstone.tracking.common.BaseEntity;
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

/** blueprint.md §8 Data Model -> Topic entity. Managed by Admin (FR-005). */
@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String topicCode;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    /** EAGER: TopicResponse always reads adminName, and open-in-view is disabled (application.yml), so
     * LAZY here throws LazyInitializationException once the controller maps the entity after the
     * transactional service call returns. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private TopicStatus status = TopicStatus.DRAFT;
}
