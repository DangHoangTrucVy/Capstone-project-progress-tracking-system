package com.capstone.tracking.audit;

import com.capstone.tracking.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * blueprint.md §11: "Mọi thao tác thay đổi trạng thái Booking, cập nhật điểm đánh giá, xác nhận biên bản
 * đều được ghi vào bảng SystemAuditTrail bất biến (Immutable)." NFR-006 requires timestamp + user id for
 * every such action.
 *
 * <p>Deliberately does NOT extend {@link com.capstone.tracking.common.BaseEntity}: that base stamps an
 * {@code updatedAt} via JPA auditing, which implies a row can change after insert. A row here never does —
 * {@link AuditService} only ever calls {@code save}, and no service should ever call {@code save} again on
 * an entity it already persisted (there is intentionally no setter to reuse).</p>
 */
@Entity
@Table(name = "system_audit_trail")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemAuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** e.g. "Booking", "EvaluationRecord", "MeetingMinute" — matches the entity's simple class name. */
    @Column(nullable = false)
    private String entityName;

    @Column(nullable = false)
    private UUID entityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AuditAction action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by", nullable = false)
    private User performedBy;

    @Column(nullable = false)
    private Instant performedAt;

    /** Free-form JSON snapshot of what changed — kept as text so this table never needs a migration of its own. */
    @Column(columnDefinition = "TEXT")
    private String detailsJson;
}
