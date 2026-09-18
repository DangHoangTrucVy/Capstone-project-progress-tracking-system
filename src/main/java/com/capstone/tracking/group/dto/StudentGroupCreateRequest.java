package com.capstone.tracking.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StudentGroupCreateRequest(
        @NotBlank String groupCode,
        @NotNull UUID topicId,
        @NotNull UUID supervisorId,
        @NotBlank String semester
) {
}
