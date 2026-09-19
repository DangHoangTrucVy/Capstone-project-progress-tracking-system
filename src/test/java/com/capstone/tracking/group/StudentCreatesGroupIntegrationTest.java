package com.capstone.tracking.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** A STUDENT who creates a group becomes its GROUP_LEADER and cannot create a second one. */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StudentCreatesGroupIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void studentCreatingGroupBecomesLeaderAndCannotCreateAnother() throws Exception {
        String token = register("creator1@fpt.edu.vn");

        mockMvc.perform(post("/api/v1/groups")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("groupCode", "GS-1", "semester", "Spring2026"))))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/auth/me").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("GROUP_LEADER"));

        mockMvc.perform(post("/api/v1/groups")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("groupCode", "GS-2", "semester", "Spring2026"))))
                .andExpect(status().isForbidden());
    }

    private String register(String email) throws Exception {
        var payload = Map.of("email", email, "fullName", "Creator", "password", "Password123");
        return objectMapper.readTree(
                mockMvc.perform(post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(payload)))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString()
        ).get("accessToken").asText();
    }
}
