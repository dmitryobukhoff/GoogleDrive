package ru.dmitryobukhoff.authservice.service;

import ru.dmitryobukhoff.authservice.model.dto.request.UserAuthRequest;
import ru.dmitryobukhoff.authservice.model.dto.request.UserRegistrationRequest;
import ru.dmitryobukhoff.authservice.model.dto.response.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse authenticate(UserAuthRequest request);
    AuthenticationResponse register(UserRegistrationRequest request);
}
