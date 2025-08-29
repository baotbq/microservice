package com.example.service;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequest;
import com.example.dto.ValidateResponse;
import com.example.model.User;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {
        if (userService.validateCredentials(request.getUsername(), request.getPassword())) {
            User user = userService.findByUsername(request.getUsername()).get();
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

            return new AuthResponse(token, user.getUsername(), user.getRole(), "Login successful");
        } else {
            return new AuthResponse(null, null, null, "Invalid credentials");
        }
    }

    public ValidateResponse validateToken(String token) {
        try {
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);
                return new ValidateResponse(true, username, role, "Token is valid");
            } else {
                return new ValidateResponse(false, null, null, "Token is invalid or expired");
            }
        } catch (Exception e) {
            return new ValidateResponse(false, null, null, "Token validation error: " + e.getMessage());
        }
    }

    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }

    public String extractRole(String token) {
        return jwtUtil.extractRole(token);
    }
}
