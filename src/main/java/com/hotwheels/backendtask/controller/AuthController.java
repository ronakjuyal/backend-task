package com.hotwheels.backendtask.controller;

import com.hotwheels.backendtask.dto.ApiResponse;
import com.hotwheels.backendtask.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginRequest req) {
        // authenticate credentials (will throw exception if invalid)
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        String token = jwtUtil.generateToken(req.getUsername());
        return ResponseEntity.ok(ApiResponse.<Map<String, String>>builder()
                .success(true)
                .data(Map.of("token", token))
                .message("Login successful")
                .build());
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
