package com.example.driveservice.service.impl;

import com.example.driveservice.exception.EntityNotFoundException;
import com.example.driveservice.repository.UserRepository;
import com.example.driveservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UUID getIdByEmail(String email){
        return userRepository.findIdByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Entity user with email '{}' not found", email);
                    return new EntityNotFoundException("Entity User not found by email: " + email);
                });
    }
}
