package com.capstone.tracking.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionBankRepository extends JpaRepository<QuestionBankItem, UUID> {

    Page<QuestionBankItem> findByTopicId(UUID topicId, Pageable pageable);

    Page<QuestionBankItem> findByTopicIdAndCategoryIgnoreCase(UUID topicId, String category, Pageable pageable);
}
