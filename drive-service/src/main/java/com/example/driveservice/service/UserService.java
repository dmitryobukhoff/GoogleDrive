package com.example.driveservice.service;

import java.util.UUID;

public interface UserService {

    UUID getIdByEmail(String email);
}
