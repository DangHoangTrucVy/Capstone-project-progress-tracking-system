package com.capstone.tracking.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SystemAuditTrailRepository extends JpaRepository<SystemAuditTrail, UUID> {

    Page<SystemAuditTrail> findByEntityNameAndEntityId(String entityName, UUID entityId, Pageable pageable);
}
