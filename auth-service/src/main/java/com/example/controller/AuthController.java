package com.example.controller;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequest;
import com.example.dto.ValidateResponse;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);

        if (response.getToken() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateResponse> validateToken(@RequestParam String token) {
        ValidateResponse response = authService.validateToken(token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/extract-username")
    public ResponseEntity<String> extractUsername(@RequestParam String token) {
        try {
            String username = authService.extractUsername(token);
            return ResponseEntity.ok(username);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    @GetMapping("/extract-role")
    public ResponseEntity<String> extractRole(@RequestParam String token) {
        try {
            String role = authService.extractRole(token);
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth Service is running!");
    }
}
