package com.capstone.tracking.group;

import com.capstone.tracking.common.BaseEntity;
import com.capstone.tracking.topic.Topic;
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

/** blueprint.md §8 Data Model -> StudentGroup entity. */
@Entity
@Table(name = "student_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentGroup extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String groupCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id", nullable = false)
    private User supervisor;

    /** e.g. "Fall2026" — matches C-001's 15-week semester framing. */
    @Column(nullable = false)
    private String semester;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private GroupStatus status = GroupStatus.FORMED;
}
