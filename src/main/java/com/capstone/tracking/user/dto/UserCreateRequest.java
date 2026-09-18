package com.capstone.tracking.user.dto;

import com.capstone.tracking.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Used by Admin to create accounts directly (FR-010), e.g. bulk-importing instructors/students. */
public record UserCreateRequest(
        @NotBlank @Email String email,
        @NotBlank String fullName,
        @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String password,
        @NotNull Role role
) {
}
