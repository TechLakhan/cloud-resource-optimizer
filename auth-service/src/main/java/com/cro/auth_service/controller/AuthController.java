package com.cro.auth_service.controller;

import com.cro.auth_service.dto.LoginRequest;
import com.cro.auth_service.dto.LoginResponse;
import com.cro.auth_service.dto.RegisterRequest;
import com.cro.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String issuedToken = authService.login(
                request.getUsername(), request.getPassword(), request.getRole()
        );
        return ResponseEntity.ok(new LoginResponse(issuedToken, request.getUsername(), request.getRole()));
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("X-CRO-Username") String username,
                                            @RequestHeader("X-CRO-Role") String role ) {
        return ResponseEntity.ok(
                Map.of("username", username,
        "role", role)
        );
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader ("X-CRO-Username") String username) {
        return ResponseEntity.ok(
                Map.of("valid", true,
                        "username", username)
        );
    }

    @GetMapping("/admin/alerts")
    public ResponseEntity<?> configureAlert(@RequestHeader ("X-CRO-Role") String role) {
        if (!"ROLE-ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Admin access required");
        }
        return ResponseEntity.ok("Alert configured.");
    }

    @GetMapping("/user/resource")
    public ResponseEntity<?> getUserResources(@RequestHeader("X-CRO-Username")
                                              String username) {
        return ResponseEntity.ok("Resources for " + username);
    }




}
