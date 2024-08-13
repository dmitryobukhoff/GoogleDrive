package ru.dmitryobukhoff.apigateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomRequestLimiterFilter implements GlobalFilter, Ordered {

    @Value("${spring.cloud.gateway.limiter.maxCapacity}")
    private int maxCapacity;

    @Value("${spring.cloud.gateway.limiter.leakRate}")
    private int leakRate;

    private ConcurrentLinkedQueue<Long> bucket = new ConcurrentLinkedQueue<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long time = System.currentTimeMillis();
        cleanBucket(time);

        if(bucket.size() == maxCapacity){
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }

        bucket.add(time);
        return chain.filter(exchange);
    }

    private void cleanBucket(long time){
        while(!bucket.isEmpty() && (time - bucket.peek()) > leakRate)
            bucket.poll();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
