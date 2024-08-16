package ru.dmitryobukhoff.authservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(Claims claims, UserDetails userDetails);
    Claims extractAllClaims(String token);
}
