package com.capstone.tracking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CapstoneTrackingBackendApplicationTests {

    @Test
    void contextLoads() {
        // Fails fast if any bean (security filter chain, JPA repositories, etc.) is misconfigured.
    }
}
