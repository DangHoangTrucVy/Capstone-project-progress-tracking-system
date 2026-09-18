package com.capstone.tracking.group.dto;

import com.capstone.tracking.group.GroupStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StudentGroupUpdateRequest(
        @NotNull UUID supervisorId,
        @NotNull GroupStatus status
) {
}
