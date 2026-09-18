package com.capstone.tracking.meeting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MeetingMinuteRepository extends JpaRepository<MeetingMinute, UUID> {

    Optional<MeetingMinute> findBySessionId(UUID sessionId);
}
