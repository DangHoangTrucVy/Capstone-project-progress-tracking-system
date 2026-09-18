package com.capstone.tracking.user.dto;

import com.capstone.tracking.user.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateRequest(
        @NotBlank String fullName,
        String avatarUrl,
        @NotNull UserStatus status
) {
}
