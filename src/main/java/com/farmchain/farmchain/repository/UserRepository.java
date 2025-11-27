package com.farmchain.farmchain.repository;

import java.util.Optional;

import com.farmchain.farmchain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;




public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    Boolean existsByEmail(String email);

}