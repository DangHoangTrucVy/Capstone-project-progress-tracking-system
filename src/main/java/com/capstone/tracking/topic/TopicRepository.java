package com.capstone.tracking.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {

    boolean existsByTopicCodeIgnoreCase(String topicCode);

    Page<Topic> findByStatus(TopicStatus status, Pageable pageable);
}
