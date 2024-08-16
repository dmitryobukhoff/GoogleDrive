package com.example.driveservice.service.impl;

import com.example.driveservice.model.dto.request.FolderContentRequest;
import com.example.driveservice.model.dto.request.FolderDeleteRequest;
import com.example.driveservice.model.dto.request.FolderLoadRequest;
import com.example.driveservice.model.dto.request.FolderRenameRequest;
import com.example.driveservice.model.dto.response.FolderContentResponse;
import com.example.driveservice.repository.FolderStorageRepository;
import com.example.driveservice.service.FolderStorageService;
import com.example.driveservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

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

    @Override
    public List<FolderContentResponse> getAll(FolderContentRequest request) {
        UUID userId = userService.getIdByEmail(request.getEmail());
        String path = createPath(userId, request.getPath().equals("/") ? "" : request.getPath());
        return repository.getAll(path);
    }

    private String createPath(UUID userId, String path){
        return "user-" + userId.toString() + "-file" + path + "/";
    }


}
