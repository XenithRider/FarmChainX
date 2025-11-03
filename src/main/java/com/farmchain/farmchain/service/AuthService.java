package com.farmchain.farmchain.service;

import com.farmchain.farmchain.dto.AuthResponse;
import com.farmchain.farmchain.dto.LoginRequest;
import com.farmchain.farmchain.dto.RegisterRequest;
import com.farmchain.farmchain.model.Role;
import com.farmchain.farmchain.model.User;
import com.farmchain.farmchain.repository.RoleRepository;
import com.farmchain.farmchain.repository.UserRepository;
import com.farmchain.farmchain.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    // Dependencies injected via constructor
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Constructor injection for better testability and immutability
    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Handles user registration logic.
     * Validates email uniqueness, prevents admin sign-up, assigns role, and saves user.
     *
     * @param request RegisterRequest containing name, email, password, and role
     * @return Success or error message
     */
    public String register(RegisterRequest request) {
        try {
            // Check if email already exists
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return "Email already exists!";
            }

            // Normalize role input to uppercase
            String roleInput = request.getRole().toUpperCase();

            // Prevent direct registration as ADMIN
            if (roleInput.equals("ADMIN") || roleInput.equals("ROLE_ADMIN")) {
                return "Cannot register as Admin!";
            }

            // Ensure role has "ROLE_" prefix
            String chosenRole = roleInput.startsWith("ROLE_") ? roleInput : "ROLE_" + roleInput;

            // Fetch role from repository
            Role userRole = roleRepository.findByName(chosenRole)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + chosenRole));

            // Create new user and populate fields
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt password
            user.setRoles(Set.of(userRole)); // Assign role

            // Save user to database
            userRepository.save(user);

            return "User registered successfully as " + chosenRole + "!";

        } catch (RuntimeException e) {
            e.printStackTrace(); // Log error for debugging
            return " Registration failed: " + e.getMessage();
        }
    }

    /**
     * Handles user login logic.
     * Validates credentials and generates JWT token.
     *
     * @param login LoginRequest containing email and password
     * @return AuthResponse with token, role, and email
     */
    public AuthResponse login(LoginRequest login) {
        // Find user by email
        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate password
        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Extract role name from user roles
        String role = user.getRoles().iterator().next().getName();

        // Generate JWT token with email, role, and user ID
        String token = jwtUtil.generateToken(user.getEmail(), role, user.getId());

        // Return authentication response
        return new AuthResponse(token, role, user.getEmail());
    }
}