package com.capstone.tracking.group.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

/** topicId/supervisorId are optional at creation — groups can form before a topic/supervisor is assigned; see {@link com.capstone.tracking.group.dto.StudentGroupUpdateRequest} to set them later. */
public record StudentGroupCreateRequest(
        @NotBlank String groupCode,
        UUID topicId,
        UUID supervisorId,
        @NotBlank String semester
) {
}
