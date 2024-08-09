package ru.dmitryobukhoff.apigateway.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.dmitryobukhoff.apigateway.repository.RequestLimiterRepository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RequestLimiterRepositoryImpl implements RequestLimiterRepository {

    private final RedisTemplate<String, Integer> template;
    private final long expiration = 60000;

    @Override
    public boolean contains(String key) {
        return template.hasKey(key);
    }

    @Override
    public Integer get(String key) {
        return template.opsForValue().get(key);
    }

    @Override
    public void put(String key, Integer value) {
        template.opsForValue().set(key, value);
        template.expire(key, Duration.ofMillis(expiration));
    }

}
