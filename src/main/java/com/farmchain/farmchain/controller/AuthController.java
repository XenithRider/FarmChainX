package com.farmchain.farmchain.controller;

import com.farmchain.farmchain.dto.AuthResponse;
import com.farmchain.farmchain.dto.LoginRequest;
import com.farmchain.farmchain.dto.RegisterRequest;
import com.farmchain.farmchain.repository.UserRepository;
import com.farmchain.farmchain.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest register) {

        // ✅ Validate email exists
        if (userRepository.existsByEmail(register.getEmail())) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Email already exists!")
            );
        }

        // ✅ Validate required fields
        if (register.getName() == null || register.getName().isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Name is required")
            );
        }

        if (register.getEmail() == null || register.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Email is required")
            );
        }

        if (register.getPassword() == null || register.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Password is required")
            );
        }

        if (register.getRole() == null || register.getRole().isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Role is required")
            );
        }

        try {
            AuthResponse response = authService.register(register);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getReason())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        try {
            AuthResponse response = authService.login(login);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }
}