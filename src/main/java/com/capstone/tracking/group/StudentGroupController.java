package com.capstone.tracking.group;

import com.capstone.tracking.group.dto.AddMemberRequest;
import com.capstone.tracking.group.dto.GroupMemberResponse;
import com.capstone.tracking.group.dto.StudentGroupCreateRequest;
import com.capstone.tracking.group.dto.StudentGroupResponse;
import com.capstone.tracking.group.dto.StudentGroupUpdateRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/** FR-010: group roster management (blueprint.md §7 UC not explicit, underpins UC-002..UC-004). */
@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@Tag(name = "Student Groups", description = "Group roster and membership management")
@SecurityRequirement(name = "bearerAuth")
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    public ResponseEntity<StudentGroupResponse> create(@Valid @RequestBody StudentGroupCreateRequest request) {
        StudentGroup created = studentGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/groups/" + created.getId()))
                .body(StudentGroupResponse.from(created));
    }

    @GetMapping
    public Page<StudentGroupResponse> list(@RequestParam(required = false) UUID supervisorId,
                                            @RequestParam(required = false) UUID topicId,
                                            Pageable pageable) {
        return studentGroupService.list(supervisorId, topicId, pageable).map(StudentGroupResponse::from);
    }

    @GetMapping("/{id}")
    public StudentGroupResponse getById(@PathVariable UUID id) {
        StudentGroup group = studentGroupService.getById(id);
        List<GroupMemberResponse> members = studentGroupService.listActiveMembers(id).stream()
                .map(GroupMemberResponse::from)
                .toList();
        return StudentGroupResponse.from(group, members);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    public StudentGroupResponse update(@PathVariable UUID id, @Valid @RequestBody StudentGroupUpdateRequest request) {
        return StudentGroupResponse.from(studentGroupService.update(id, request));
    }

    @PostMapping("/{id}/members")
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','GROUP_LEADER')")
    public ResponseEntity<GroupMemberResponse> addMember(@PathVariable UUID id, @Valid @RequestBody AddMemberRequest request) {
        GroupMember member = studentGroupService.addMember(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(GroupMemberResponse.from(member));
    }

    @DeleteMapping("/{id}/members/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR','GROUP_LEADER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMember(@PathVariable UUID id, @PathVariable UUID memberId) {
        studentGroupService.removeMember(id, memberId);
    }
}
