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
    public void run(String... args) throws Exception {

        String[] roles = {
                "ROLE_CONSUMER",
                "ROLE_FARMER",
                "ROLE_DISTRIBUTER",
                "ROLE_RETAILER",
                "ROLE_ADMIN"
        };

        for (String roleName : roles) {
            // ✅ use the correct repository method and field name
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName); // ✅ correct setter for Role.name
                roleRepository.save(role);
            }
        }

        System.out.println("✅ Role seeding completed successfully!");
    }
}