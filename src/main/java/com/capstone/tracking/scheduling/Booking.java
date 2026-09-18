package com.capstone.tracking.scheduling;

import com.capstone.tracking.common.BaseEntity;
import com.capstone.tracking.group.StudentGroup;
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

/** blueprint.md §8 Data Model -> Booking entity (Sprint 2 — API-003, API-004). */
@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false)
    private ScheduleSlot slot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private StudentGroup group;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private BookingStatus bookingStatus = BookingStatus.CONFIRMED;

    @Column(nullable = false)
    private Instant bookedAt;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private Instant cancelledAt;
}
