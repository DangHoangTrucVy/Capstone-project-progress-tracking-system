package com.capstone.tracking.topic.dto;

import com.capstone.tracking.topic.Topic;
import com.capstone.tracking.topic.TopicStatus;

import java.util.UUID;

public record TopicResponse(
        UUID id,
        String topicCode,
        String title,
        String description,
        String category,
        UUID adminId,
        String adminName,
        TopicStatus status
) {
    public static TopicResponse from(Topic t) {
        return new TopicResponse(t.getId(), t.getTopicCode(), t.getTitle(), t.getDescription(), t.getCategory(),
                t.getAdmin().getId(), t.getAdmin().getFullName(), t.getStatus());
    }
}
