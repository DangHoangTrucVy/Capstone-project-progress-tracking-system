package com.capstone.tracking.audit;

import com.capstone.tracking.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Single write path for {@link SystemAuditTrail} rows (NFR-006). Call {@link #record} from inside the
 * same business transaction as the change it documents (e.g. {@code BookingService.cancel}) — Postgres
 * then guarantees the audit row and the state change commit or roll back together, which a fire-and-forget
 * logger or a separate async write could not.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final SystemAuditTrailRepository repository;
    private final ObjectMapper objectMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public void record(String entityName, UUID entityId, AuditAction action, User performedBy, Map<String, Object> details) {
        String json;
        try {
            json = objectMapper.writeValueAsString(details == null ? Map.of() : details);
        } catch (Exception e) {
            // Never let a serialization hiccup block the business transaction the audit row rides along with.
            log.warn("Could not serialize audit details for {}#{}: {}", entityName, entityId, e.getMessage());
            json = "{}";
        }

        SystemAuditTrail entry = SystemAuditTrail.builder()
                .entityName(entityName)
                .entityId(entityId)
                .action(action)
                .performedBy(performedBy)
                .performedAt(Instant.now())
                .detailsJson(json)
                .build();
        repository.save(entry);
    }
}
