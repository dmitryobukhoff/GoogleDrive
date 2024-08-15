package com.example.driveservice.filter;

import com.example.driveservice.exception.JwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtProcessingFilter extends OncePerRequestFilter implements Ordered {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Value("${security.jwt.public-key}")
    private String publicKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            handlerExceptionResolver.resolveException(request, response, null, new JwtTokenException("No 'Authorization' header"));
            return;
        }
        final String token = authorization.substring(7);
        try{
                Jwts.parserBuilder()
                    .setSigningKey(getPublicKey(publicKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            filterChain.doFilter(request, response);
        } catch (SignatureException exception){
            handlerExceptionResolver.resolveException(request, response, null, new JwtTokenException("Signature is incorrect"));
        } catch (ExpiredJwtException exception) {
            handlerExceptionResolver.resolveException(request, response, null, new JwtTokenException("Token is expired"));
        } catch (Exception exception){
            handlerExceptionResolver.resolveException(request, response, null, new JwtTokenException(exception));
        }
    }

    private PublicKey getPublicKey(String publicKey){
        try {
            byte[] bytes = Base64.getDecoder().decode(publicKey.replaceAll("\\s+", ""));
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            return KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException exception) {
            throw new JwtTokenException(exception);
        }
    }


    @Override
    public int getOrder() {
        return -1;
    }
}
