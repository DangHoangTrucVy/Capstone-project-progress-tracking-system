package com.capstone.tracking.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Self-service sign-up, restricted to the university email domain (A-005 in blueprint.md §4).
 * Always creates a STUDENT account — promotion to Group Leader/Instructor/Admin is an explicit
 * action taken later by an Admin (or the group-leader flag on GroupMember), never at sign-up.
 */
public record RegisterRequest(
        @NotBlank @Email String email,
        @NotBlank String fullName,
        @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String password
) {
}
