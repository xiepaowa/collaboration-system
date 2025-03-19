package com.example.collaborationsystem.repository;

import com.example.collaborationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, Long id);
    User findByUsername(String username);
}