package com.capstone.tracking.topic.dto;

import jakarta.validation.constraints.NotBlank;

public record TopicCreateRequest(
        @NotBlank String topicCode,
        @NotBlank String title,
        String description,
        String category
) {
}
