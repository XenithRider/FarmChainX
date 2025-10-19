package com.farmchain.farmchain.service;

import com.farmchain.farmchain.dto.RegisterRequest;
import com.farmchain.farmchain.model.Role;
import com.farmchain.farmchain.model.User;
import com.farmchain.farmchain.repository.RoleRepository;
import com.farmchain.farmchain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest request) {
        // 1. Check if user already exists by email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists!";
        }

        // 2. Get requested role from DTO
        String chosenRole = request.getRole().toUpperCase();

        // 3. Prevent direct ADMIN registration
        if (chosenRole.equals("ADMIN")) {
            return "Cannot register as Admin";
        }

        // 4. Build full role name
        String roleName = "ROLE_" + chosenRole;

        // 5. Fetch role from DB
        Role userRole = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        // 6. Create new User object
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(userRole));

        // 7. Save user to DB
        userRepository.save(user);

        return "User registered successfully!";
    }
}