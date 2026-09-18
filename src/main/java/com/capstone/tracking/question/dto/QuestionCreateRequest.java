package com.capstone.tracking.question.dto;

import jakarta.validation.constraints.NotBlank;

public record QuestionCreateRequest(
        String category,
        @NotBlank String questionText,
        String guidanceNotes
) {
}
