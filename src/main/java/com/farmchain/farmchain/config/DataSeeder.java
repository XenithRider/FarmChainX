package com.farmchain.farmchain.config;

import com.farmchain.farmchain.model.Role;
import com.farmchain.farmchain.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Set<String> roles = Set.of("ROLE_USER", "ROLE_ADMIN", "ROLE_CONSUMER", "ROLE_FARMER", "ROLE_DISTRIBUTOR");

        for (String roleName : roles) {
            seedRole(roleName);
        }
    }

    private void seedRole(String roleName) {
        if (!roleRepository.existsByRoleName(roleName)) {
            Role role = new Role();
            role.setRoleName(roleName);
            roleRepository.save(role);
            System.out.println(LocalDateTime.now() + " - " + roleName + " created");
        } else {
            System.out.println(LocalDateTime.now() + " - " + roleName + " already exists");
        }
    }
}