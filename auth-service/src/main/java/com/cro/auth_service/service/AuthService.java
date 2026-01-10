package com.cro.auth_service.service;

import com.cro.auth_service.dto.LoginRequest;
import com.cro.auth_service.dto.LoginResponse;
import com.cro.auth_service.dto.RegisterRequest;
import com.cro.auth_service.entity.User;
import com.cro.auth_service.entity.UserRepository;
import com.cro.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .role(request.getRole())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(newUser);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials.");
        }
        String trustToken = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new LoginResponse(trustToken, user.getUsername(), user.getRole());
    }
}
