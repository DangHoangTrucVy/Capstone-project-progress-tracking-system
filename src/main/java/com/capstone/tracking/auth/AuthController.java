package com.capstone.tracking.auth;

import com.capstone.tracking.auth.dto.LoginRequest;
import com.capstone.tracking.auth.dto.LoginResponse;
import com.capstone.tracking.auth.dto.RegisterRequest;
import com.capstone.tracking.user.User;
import com.capstone.tracking.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Registration, login, and current-session identity")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public LoginResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /** Reads the identity Spring Security already resolved from the Bearer token via JwtAuthenticationFilter. */
    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal User currentUser) {
        return UserResponse.from(currentUser);
    }
}
