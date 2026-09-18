package com.capstone.tracking.meeting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequirementLogRepository extends JpaRepository<RequirementLog, UUID> {

    Page<RequirementLog> findBySessionId(UUID sessionId, Pageable pageable);

    Page<RequirementLog> findByGroupIdAndStatus(UUID groupId, RequirementStatus status, Pageable pageable);
}
