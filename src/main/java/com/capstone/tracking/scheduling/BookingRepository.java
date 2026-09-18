package com.capstone.tracking.scheduling;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    /**
     * UC-002 precondition: "Nhóm chưa đặt slot nào trong cùng đợt kiểm tra hiện hành." v1 simplifies
     * "đợt kiểm tra" to "system-wide": a group may only hold one CONFIRMED booking at a time. Revisit
     * once an explicit assessment-round entity exists, scoping this check to that round instead.
     */
    boolean existsByGroupIdAndBookingStatus(UUID groupId, BookingStatus bookingStatus);
}
