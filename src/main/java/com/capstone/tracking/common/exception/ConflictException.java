package com.capstone.tracking.common.exception;

import org.springframework.http.HttpStatus;

/** Used for state conflicts, e.g. duplicate email, time-slot overlap, already-booked group. */
public class ConflictException extends ApiException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, "CONFLICT", message);
    }
}
