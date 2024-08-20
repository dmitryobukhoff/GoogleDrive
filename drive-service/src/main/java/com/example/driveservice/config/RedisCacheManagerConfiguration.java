package com.example.driveservice.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public class RedisCacheManagerConfiguration {

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(){
        return (builder) -> { builder
                .withCacheConfiguration("file_cache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)))
                .withCacheConfiguration("folder_cache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(15)));
        };
    }
}
