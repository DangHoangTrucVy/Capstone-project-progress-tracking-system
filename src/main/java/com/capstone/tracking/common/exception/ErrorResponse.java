package com.capstone.tracking.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        Instant timestamp,
        int status,
        String errorCode,
        String message,
        String path,
        List<FieldError> fieldErrors
) {
    public record FieldError(String field, String message) {
    }

    public static ErrorResponse of(int status, String errorCode, String message, String path) {
        return new ErrorResponse(Instant.now(), status, errorCode, message, path, null);
    }

    public static ErrorResponse ofValidation(int status, String message, String path, List<FieldError> fieldErrors) {
        return new ErrorResponse(Instant.now(), status, "VALIDATION_FAILED", message, path, fieldErrors);
    }
}
