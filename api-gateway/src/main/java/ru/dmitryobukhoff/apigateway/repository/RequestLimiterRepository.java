package ru.dmitryobukhoff.apigateway.repository;

public interface RequestLimiterRepository {
    Boolean contains(String key);
    Integer get(String key);
    void put(String key, Integer value);
}
