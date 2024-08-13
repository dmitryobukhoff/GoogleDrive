package ru.dmitryobukhoff.apigateway.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.dmitryobukhoff.apigateway.repository.RequestLimiterRepository;
import ru.dmitryobukhoff.apigateway.service.RequestLimiterService;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestLimiterServiceImpl implements RequestLimiterService {

    private final RequestLimiterRepository limiterRepository;

    @Value("${spring.cloud.gateway.limiter.requestPerMinute}")
    private Integer limitPerMinute ;

    @Override
    public Integer get(String key) {
        return limiterRepository.get(key);
    }

    @Override
    public void set(String key, Integer value) {
        limiterRepository.put(key, value);
    }

    @Override
    public boolean contains(String key) {
        Boolean contains = limiterRepository.contains(key);
        return contains != null && contains;
    }

    @Override
    public int increase(String key){
        int amount = this.contains(key) ? get(key) + 1 : 1;
        this.set(key, amount);
        return amount;
    }

    @Override
    public boolean isIncreaseAllowed(String key){
        return !this.contains(key) || (this.get(key) + 1 <= limitPerMinute);
    }
}
