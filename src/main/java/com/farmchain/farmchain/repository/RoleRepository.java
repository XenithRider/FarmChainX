package com.farmchain.farmchain.repository;

import com.farmchain.farmchain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    boolean existsByRoleName(String roleName) ;  // check if role exists
    Optional<Role> findByRoleName(String roleName) ; //find role by name
}
