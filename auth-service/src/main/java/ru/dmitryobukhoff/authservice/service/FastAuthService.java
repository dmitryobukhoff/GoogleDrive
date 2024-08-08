package ru.dmitryobukhoff.authservice.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface FastAuthService {
    boolean contains(String key);
    UserDetails save(String key, UserDetails userDetails);
    UserDetails get(String token);
}
