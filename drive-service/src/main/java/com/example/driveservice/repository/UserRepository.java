package com.example.driveservice.repository;

import com.example.driveservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT user.id FROM User user WHERE user.email = ?1")
    UUID getIdByEmail(String email);
}
