package com.example.driveservice.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Base64;

@Component
public class JwtHandleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = extractToken(request);
        String payload = extractPayload(token);
        String username = extractUsername(payload);
        request.setAttribute("username", username);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    private String extractToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        return header.substring(7);
    }

    private String extractPayload(String token){
        return token.split("\\.")[1];
    }

    private String extractUsername(String payload) throws JsonProcessingException {
        String decoded = new String(Base64.getDecoder().decode(payload));
        JsonNode node = new ObjectMapper().readTree(decoded);
        return node.get("sub").asText();
    }
}
