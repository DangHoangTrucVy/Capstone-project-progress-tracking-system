package com.capstone.tracking.scheduling.dto;

import jakarta.validation.constraints.NotBlank;

/** Matches API-004's request payload. */
public record CancelBookingRequest(
        @NotBlank String reason
) {
}
