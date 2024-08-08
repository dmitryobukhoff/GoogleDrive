package ru.dmitryobukhoff.authservice.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ru.dmitryobukhoff.authservice.repository.FastAuthRepository;

@Repository
@RequiredArgsConstructor
public class FastAuthRepositoryImpl implements FastAuthRepository {

    private final RedisTemplate<String, UserDetails> template;

    @Override
    public Boolean contains(String token) {
        return template.hasKey(token);
    }

    @Override
    public UserDetails put(String token, UserDetails user) {
        template.opsForValue().set(token, user);
        return user;
    }

    @Override
    public UserDetails get(String token) {
        return template.opsForValue().get(token);
    }
}
