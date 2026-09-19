package com.capstone.tracking.group;

import com.capstone.tracking.common.exception.ConflictException;
import com.capstone.tracking.group.dto.AddMemberRequest;
import com.capstone.tracking.group.dto.StudentGroupCreateRequest;
import com.capstone.tracking.user.Role;
import com.capstone.tracking.user.User;
import com.capstone.tracking.user.UserRepository;
import com.capstone.tracking.user.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class GroupMaxMembersIntegrationTest {

    @Autowired
    private StudentGroupService groupService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void groupRejectsSixthMember() {
        User admin = User.builder().email("cap-admin@fpt.edu.vn").fullName("Admin").role(Role.ADMIN)
                .status(UserStatus.ACTIVE).build();
        StudentGroup group = groupService.create(new StudentGroupCreateRequest("CAP-1", null, null, "Spring2026"), admin);

        for (int i = 1; i <= StudentGroupService.MAX_MEMBERS; i++) {
            groupService.addMember(group.getId(), new AddMemberRequest(newStudent("cap-s" + i).getId(), false));
        }
        assertEquals(StudentGroupService.MAX_MEMBERS, groupService.listActiveMembers(group.getId()).size());

        User sixth = newStudent("cap-s6");
        assertThrows(ConflictException.class,
                () -> groupService.addMember(group.getId(), new AddMemberRequest(sixth.getId(), false)));
    }

    private User newStudent(String prefix) {
        return userRepository.save(User.builder().email(prefix + "@fpt.edu.vn").fullName(prefix)
                .passwordHash("x").role(Role.STUDENT).status(UserStatus.ACTIVE).build());
    }
}
