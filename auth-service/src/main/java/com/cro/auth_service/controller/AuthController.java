package com.cro.auth_service.controller;

import com.cro.auth_service.dto.LoginRequest;
import com.cro.auth_service.dto.LoginResponse;
import com.cro.auth_service.dto.RegisterRequest;
import com.cro.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
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
                request.getUsername(), request.getPassword()
        );
        return ResponseEntity.ok(new LoginResponse(issuedToken));
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("X-CRO-Username") String username,
                                            @RequestHeader("X-CRO-Roles") String roles ) {
        return ResponseEntity.ok(
                Map.of("username", username,
        "roles", roles)
        );
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader ("X-CRO-Username") String username) {
        return ResponseEntity.ok(
                Map.of("valid", true,
                        "username", username)
        );
    }



}
