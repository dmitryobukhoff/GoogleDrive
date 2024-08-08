package ru.dmitryobukhoff.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dmitryobukhoff.authservice.model.User;
import ru.dmitryobukhoff.authservice.model.dto.request.UserAuthRequest;
import ru.dmitryobukhoff.authservice.model.dto.request.UserRegistrationRequest;
import ru.dmitryobukhoff.authservice.model.dto.response.AuthenticationResponse;
import ru.dmitryobukhoff.authservice.repository.UserRepository;
import ru.dmitryobukhoff.authservice.service.AuthService;
import ru.dmitryobukhoff.authservice.service.JwtService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(UserRegistrationRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        log.info("User is saved. Username: {}", user.getUsername());
        final String token = jwtService.generateToken(user);
        log.info("Token is generated. jwt '{}'", token);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(UserAuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), // principal - информация, идентифицирующая пользователя
                        request.getPassword() // credentials - информация, предоставляемая пользователем для аутентификации
                )
        );
        log.info("User '{}' saved in Security Context", request.getEmail());
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow();
        final String token = jwtService.generateToken(user);
        log.info("Token is generated. jwt '{}'", token);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
