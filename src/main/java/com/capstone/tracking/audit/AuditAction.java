package com.capstone.tracking.audit;

/** The kinds of state transitions blueprint.md §11 requires to be traceable. */
public enum AuditAction {
    CREATE,
    UPDATE,
    CANCEL,
    APPROVE,
    REJECT,
    SIGN
}
