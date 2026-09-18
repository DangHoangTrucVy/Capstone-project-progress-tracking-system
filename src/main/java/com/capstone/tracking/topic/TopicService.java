package com.capstone.tracking.topic;

import com.capstone.tracking.common.exception.ConflictException;
import com.capstone.tracking.common.exception.ResourceNotFoundException;
import com.capstone.tracking.topic.dto.TopicCreateRequest;
import com.capstone.tracking.topic.dto.TopicUpdateRequest;
import com.capstone.tracking.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicService {

    private final TopicRepository topicRepository;

    @Transactional
    public Topic create(TopicCreateRequest request, User admin) {
        if (topicRepository.existsByTopicCodeIgnoreCase(request.topicCode())) {
            throw new ConflictException("Topic code " + request.topicCode() + " is already in use");
        }
        Topic topic = Topic.builder()
                .topicCode(request.topicCode())
                .title(request.title())
                .description(request.description())
                .category(request.category())
                .admin(admin)
                .status(TopicStatus.DRAFT)
                .build();
        return topicRepository.save(topic);
    }

    public Topic getById(UUID id) {
        return topicRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.of("Topic", id));
    }

    public Page<Topic> list(TopicStatus status, Pageable pageable) {
        return status == null ? topicRepository.findAll(pageable) : topicRepository.findByStatus(status, pageable);
    }

    @Transactional
    public Topic update(UUID id, TopicUpdateRequest request) {
        Topic topic = getById(id);
        topic.setTitle(request.title());
        topic.setDescription(request.description());
        topic.setCategory(request.category());
        topic.setStatus(request.status());
        return topic;
    }
}
