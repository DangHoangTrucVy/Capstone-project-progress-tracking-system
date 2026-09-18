package com.capstone.tracking.question.dto;

import com.capstone.tracking.question.QuestionBankItem;
import com.capstone.tracking.question.QuestionStatus;

import java.util.UUID;

public record QuestionResponse(
        UUID id,
        UUID topicId,
        String category,
        String questionText,
        String guidanceNotes,
        QuestionStatus status
) {
    public static QuestionResponse from(QuestionBankItem q) {
        return new QuestionResponse(q.getId(), q.getTopic().getId(), q.getCategory(),
                q.getQuestionText(), q.getGuidanceNotes(), q.getStatus());
    }
}
