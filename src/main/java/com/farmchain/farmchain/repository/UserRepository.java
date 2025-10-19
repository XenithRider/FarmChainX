package com.farmchain.farmchain.repository;

import com.farmchain.farmchain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email) ; // find user by email
    boolean existsByEmail(String email); // check if email exists
}
