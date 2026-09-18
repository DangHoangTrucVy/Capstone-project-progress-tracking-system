package com.capstone.tracking.group.dto;

import com.capstone.tracking.group.GroupMember;
import com.capstone.tracking.group.MemberStatus;

import java.time.Instant;
import java.util.UUID;

public record GroupMemberResponse(
        UUID id,
        UUID userId,
        String userFullName,
        String userEmail,
        boolean isLeader,
        Instant joinedAt,
        MemberStatus status
) {
    public static GroupMemberResponse from(GroupMember m) {
        return new GroupMemberResponse(m.getId(), m.getUser().getId(), m.getUser().getFullName(),
                m.getUser().getEmail(), m.isLeader(), m.getJoinedAt(), m.getStatus());
    }
}
