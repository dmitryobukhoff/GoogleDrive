package ru.dmitryobukhoff.authservice.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmitryobukhoff.authservice.model.dto.request.UserAuthRequest;
import ru.dmitryobukhoff.authservice.model.dto.request.UserRegistrationRequest;
import ru.dmitryobukhoff.authservice.model.dto.response.AuthenticationResponse;
import ru.dmitryobukhoff.authservice.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody UserAuthRequest authenticateInformation
    ){
        return ResponseEntity.ok(authService.authenticate(authenticateInformation));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserRegistrationRequest registrationInformation
    ){
        return ResponseEntity.ok(authService.register(registrationInformation));
    }

}
