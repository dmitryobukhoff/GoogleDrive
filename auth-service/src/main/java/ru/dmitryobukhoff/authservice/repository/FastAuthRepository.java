package ru.dmitryobukhoff.authservice.repository;

import org.springframework.security.core.userdetails.UserDetails;

public interface FastAuthRepository {
   Boolean contains(String token);
   UserDetails put(String token, UserDetails user);
   UserDetails get(String token);
}
