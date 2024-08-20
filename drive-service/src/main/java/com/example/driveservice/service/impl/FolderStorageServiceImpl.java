package com.example.driveservice.service.impl;

import com.example.driveservice.model.dto.request.FolderContentRequest;
import com.example.driveservice.model.dto.request.FolderDeleteRequest;
import com.example.driveservice.model.dto.request.FolderLoadRequest;
import com.example.driveservice.model.dto.request.FolderRenameRequest;
import com.example.driveservice.model.dto.response.FolderContentResponse;
import com.example.driveservice.repository.FolderStorageRepository;
import com.example.driveservice.service.FolderStorageService;
import com.example.driveservice.service.UserService;
import com.example.driveservice.util.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class FolderStorageServiceImpl implements FolderStorageService {

    private final FolderStorageRepository repository;
    private final UserService userService;

    @Override
    public void create(FolderLoadRequest request){
        UUID userId = userService.getIdByEmail(UserContext.getUsername());
        String path = createPath(userId, request.getPath());
        repository.create(path);
        log.info("User: {} create folder {} path", request.getEmail(), path);
    }

    @Override
    @CacheEvict(value = "folder_schema", key = "#request.email + ':' + #request.path")
    public void delete(FolderDeleteRequest request) {
        UUID userId = userService.getIdByEmail(request.getEmail());
        String path = createPath(userId, request.getPath());
        repository.delete(path);
        log.info("User: {} delete folder {}", request.getEmail(), path);
    }

    @Override
    public void rename(FolderRenameRequest request) {
        UUID userId = userService.getIdByEmail(request.getEmail());
        String oldPath = createPath(userId, request.getOldPath());
        String newPath = createPath(userId, request.getNewPath());
        repository.rename(oldPath, newPath);
        log.info("User: {} rename folder from {} to {}", request.getEmail(), oldPath, newPath);
    }

    @Override
    @Cacheable(value = "folder_cache", key = "#request.email + ':' + #request.path")
    public List<FolderContentResponse> getAll(FolderContentRequest request) {
        UUID userId = userService.getIdByEmail(request.getEmail());
        String path = createPath(userId, request.getPath().equals("/") ? "" : request.getPath());
        log.info("User: {} check all files in folder {}", request.getEmail(), path);
        return repository.getAll(path);
    }

    private String createPath(UUID userId, String path){
        return "user-" + userId.toString() + "-file" + path + "/";
    }


}
