package com.example.controller;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequest;
import com.example.dto.ValidateResponse;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")  // Removed "/test" for production
@CrossOrigin(origins = "*")   // Add CORS support
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            logger.debug("Login attempt for username: {}", request.getUsername());

            if (request.getUsername() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest()
                        .body(new AuthResponse(null, null, null, "Username and password are required"));
            }

            AuthResponse response = authService.login(request);

            if (response.getToken() != null) {
                logger.info("Login successful for user: {}", request.getUsername());
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Login failed for user: {}", request.getUsername());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            logger.error("Login error: ", e);
            return ResponseEntity.internalServerError()
                    .body(new AuthResponse(null, null, null, "Internal server error"));
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateResponse> validateToken(@RequestParam String token) {
        try {
            logger.debug("Validating token");

            if (token == null || token.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ValidateResponse(false, null, null, "Token is required"));
            }

            ValidateResponse response = authService.validateToken(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Token validation error: ", e);
            return ResponseEntity.internalServerError()
                    .body(new ValidateResponse(false, null, null, "Internal server error"));
        }
    }

    @GetMapping("/extract-username")
    public ResponseEntity<String> extractUsername(@RequestParam String token) {
        try {
            logger.debug("Extracting username from token");

            if (token == null || token.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Token is required");
            }

            String username = authService.extractUsername(token);
            return ResponseEntity.ok(username);
        } catch (Exception e) {
            logger.error("Username extraction error: ", e);
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    @GetMapping("/extract-role")
    public ResponseEntity<String> extractRole(@RequestParam String token) {
        try {
            logger.debug("Extracting role from token");

            if (token == null || token.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Token is required");
            }

            String role = authService.extractRole(token);
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            logger.error("Role extraction error: ", e);
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        logger.info("Health check requested");
        return ResponseEntity.ok("Auth Service is running!");
    }

    // Additional endpoint to check users
    @GetMapping("/users")
    public ResponseEntity<String> getTestUsers() {
        return ResponseEntity.ok("Test users: admin/admin123, user/user123, moderator/mod123");
    }
}