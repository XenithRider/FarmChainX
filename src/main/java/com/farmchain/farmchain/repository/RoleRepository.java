package com.farmchain.farmchain.repository;

import com.farmchain.farmchain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    boolean existsByName(String name); // check if role exists
    Optional<Role> findByName(String name); //find role by name
}
