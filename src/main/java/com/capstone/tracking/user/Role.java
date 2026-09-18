package com.capstone.tracking.user;

/**
 * RBAC roles per blueprint.md §11 (Security, Privacy and Compliance).
 * Spring Security expects the "ROLE_" prefix on the granted authority, added in {@link User#getAuthorities()}.
 */
public enum Role {
    ADMIN,
    INSTRUCTOR,
    GROUP_LEADER,
    STUDENT
}
