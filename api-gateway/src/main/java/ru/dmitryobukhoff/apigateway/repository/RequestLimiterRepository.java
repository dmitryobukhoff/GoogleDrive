package ru.dmitryobukhoff.apigateway.repository;

public interface RequestLimiterRepository {
    boolean contains(String key);
    Integer get(String key);
    void put(String key, Integer value);
}
