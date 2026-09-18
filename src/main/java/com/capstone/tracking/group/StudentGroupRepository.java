package com.capstone.tracking.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, UUID> {

    boolean existsByGroupCodeIgnoreCase(String groupCode);

    Page<StudentGroup> findBySupervisorId(UUID supervisorId, Pageable pageable);

    Page<StudentGroup> findByTopicId(UUID topicId, Pageable pageable);
}
