package com.example.driveservice.service.impl;

import com.example.driveservice.model.dto.request.FolderDeleteRequest;
import com.example.driveservice.model.dto.request.FolderLoadRequest;
import com.example.driveservice.model.dto.request.FolderRenameRequest;
import com.example.driveservice.repository.FolderStorageRepository;
import com.example.driveservice.service.FolderStorageService;
import com.example.driveservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FolderStorageServiceImpl implements FolderStorageService {

    private final FolderStorageRepository repository;
    private final UserService userService;

    @Override
    public void create(FolderLoadRequest request){
        UUID userId = userService.getIdByEmail(request.getEmail());
        String path = createPath(userId, request.getPath());
        repository.create(path);
    }

    @Override
    public void delete(FolderDeleteRequest request) {
        UUID userId = userService.getIdByEmail(request.getEmail());
        String path = createPath(userId, request.getPath());
        repository.delete(path);
    }

    @Override
    public void rename(FolderRenameRequest request) {
        UUID userId = userService.getIdByEmail(request.getEmail());
        String oldPath = createPath(userId, request.getOldPath());
        String newPath = createPath(userId, request.getNewPath());
        repository.rename(oldPath, newPath);
    }

    private String createPath(UUID userId, String path){
        return "user-" + userId.toString() + "-file" + path + "/";
    }
}
