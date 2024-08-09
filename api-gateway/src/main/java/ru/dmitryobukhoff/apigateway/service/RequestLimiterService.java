package ru.dmitryobukhoff.apigateway.service;

public interface RequestLimiterService {
    Integer get(String key);
    void set(String key, Integer value);
    boolean contains(String key);
    boolean isIncreaseAllowed(String key);
    int increase(String key);
}
