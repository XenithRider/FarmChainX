package com.farmchain.farmchain.repository;

import java.util.Optional;

import com.farmchain.farmchain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    boolean existsByName(String name);
}
