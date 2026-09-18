package com.capstone.tracking.artifact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtifactSubmissionRepository extends JpaRepository<ArtifactSubmission, UUID> {

    Page<ArtifactSubmission> findByGroupId(UUID groupId, Pageable pageable);
}
