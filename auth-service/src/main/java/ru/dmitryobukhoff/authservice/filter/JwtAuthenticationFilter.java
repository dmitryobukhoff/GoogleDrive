package ru.dmitryobukhoff.authservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ru.dmitryobukhoff.authservice.service.FastAuthService;
import ru.dmitryobukhoff.authservice.service.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final FastAuthService fastAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Input request {}", request.getServletPath());
        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            log.warn("Header 'Authorization' isn't exist");
            return;
        }
        try{
            final String token = authHeader.substring(7);
            final String username = jwtService.extractUsername(token);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = null;

            if(fastAuthService.contains(token) && authentication == null){
                log.info("Token {} contains in cache", token);
                userDetails = fastAuthService.get(token);
                setInSecurityContext(userDetails, request);
                log.info("Founded user with username '{}'", userDetails.getUsername());
            }else{
                if(username != null && authentication == null){
                    userDetails = userDetailsService.loadUserByUsername(username);
                    log.info("Founded user with username '{}'", userDetails.getUsername());
                    if(jwtService.isTokenValid(token, userDetails)){
                        setInSecurityContext(userDetails, request);
                        fastAuthService.save(token, userDetails);
                        log.info("Request token added in security context. Token: {}", token);
                    }
                }
            }

            filterChain.doFilter(request, response);

        }catch (Exception exception){
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

    private void setInSecurityContext(UserDetails userDetails, HttpServletRequest request){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
