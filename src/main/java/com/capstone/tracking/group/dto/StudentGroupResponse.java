package com.capstone.tracking.group.dto;

import com.capstone.tracking.group.GroupStatus;
import com.capstone.tracking.group.StudentGroup;

import java.util.List;
import java.util.UUID;

public record StudentGroupResponse(
        UUID id,
        String groupCode,
        UUID topicId,
        String topicTitle,
        UUID supervisorId,
        String supervisorName,
        String semester,
        GroupStatus status,
        List<GroupMemberResponse> members
) {
    public static StudentGroupResponse from(StudentGroup g) {
        return from(g, null);
    }

    public static StudentGroupResponse from(StudentGroup g, List<GroupMemberResponse> members) {
        return new StudentGroupResponse(g.getId(), g.getGroupCode(), g.getTopic().getId(), g.getTopic().getTitle(),
                g.getSupervisor().getId(), g.getSupervisor().getFullName(), g.getSemester(), g.getStatus(), members);
    }
}
