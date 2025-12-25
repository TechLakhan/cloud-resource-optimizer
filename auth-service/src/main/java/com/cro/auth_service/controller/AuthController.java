package com.cro.auth_service.controller;

import com.cro.auth_service.dto.LoginRequest;
import com.cro.auth_service.dto.LoginResponse;
import com.cro.auth_service.dto.RegisterRequest;
import com.cro.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                request.getUsername(), request.getPassword()
        );
        return ResponseEntity.ok(new LoginResponse(issuedToken));
    }
}
