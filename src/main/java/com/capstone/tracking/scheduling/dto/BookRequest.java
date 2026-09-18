package com.capstone.tracking.scheduling.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/** Matches API-003's request payload. */
public record BookRequest(
        @NotNull UUID groupId,
        String notes
) {
}
