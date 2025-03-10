package com.zendev.task_management_system.user.repositories;

import com.zendev.task_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Delete user by email
    void deleteByEmail(String email);
}
