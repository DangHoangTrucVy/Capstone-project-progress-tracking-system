package com.capstone.tracking.meeting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MeetingSessionRepository extends JpaRepository<MeetingSession, UUID> {

    Optional<MeetingSession> findByBookingId(UUID bookingId);
}
