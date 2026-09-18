package com.capstone.tracking.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupMemberRepository extends JpaRepository<GroupMember, UUID> {

    @Query("SELECT gm FROM GroupMember gm JOIN FETCH gm.user WHERE gm.group.id = :groupId AND gm.status = :status")
    List<GroupMember> findByGroupIdAndStatus(@Param("groupId") UUID groupId, @Param("status") MemberStatus status);

    Optional<GroupMember> findByGroupIdAndUserIdAndStatus(UUID groupId, UUID userId, MemberStatus status);

    boolean existsByGroupIdAndUserIdAndStatus(UUID groupId, UUID userId, MemberStatus status);

    boolean existsByGroupIdAndIsLeaderTrueAndStatus(UUID groupId, MemberStatus status);
}
