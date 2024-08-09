package ru.dmitryobukhoff.apigateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.dmitryobukhoff.apigateway.service.RequestLimiterService;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomRequestLimiterFilter implements GlobalFilter, Ordered {

    private final RequestLimiterService limiterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String ip = exchange.getRequest().getRemoteAddress().getHostString();
        log.info("Get new request from {}", ip);
        if(!limiterService.isIncreaseAllowed(ip)){
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }

        int requests = limiterService.increase(ip);
        log.info("IP: {} has {} request per minute", ip, requests);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
