package ru.dmitryobukhoff.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.dmitryobukhoff.authservice.repository.FastAuthRepository;
import ru.dmitryobukhoff.authservice.service.FastAuthService;

@Service
@RequiredArgsConstructor
public class FastAuthServiceImpl implements FastAuthService {

    private final FastAuthRepository fastAuthRepository;

    @Override
    public boolean contains(String key) {
        Boolean isContains = fastAuthRepository.contains(key);
        return isContains != null && isContains;
    }

    @Override
    public UserDetails save(String key, UserDetails userDetails) {
        return fastAuthRepository.put(key, userDetails);
    }

    @Override
    public UserDetails get(String token) {
        return fastAuthRepository.get(token);
    }
}
