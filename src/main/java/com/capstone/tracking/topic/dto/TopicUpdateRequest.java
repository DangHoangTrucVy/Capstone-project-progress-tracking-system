package com.capstone.tracking.topic.dto;

import com.capstone.tracking.topic.TopicStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicUpdateRequest(
        @NotBlank String title,
        String description,
        String category,
        @NotNull TopicStatus status
) {
}
