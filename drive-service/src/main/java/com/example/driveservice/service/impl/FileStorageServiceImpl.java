package com.example.driveservice.service.impl;

import com.example.driveservice.model.dto.request.FileDeleteRequest;
import com.example.driveservice.model.dto.request.FileLoadRequest;
import com.example.driveservice.model.dto.request.FileRenameRequest;
import com.example.driveservice.repository.FileStorageRepository;
import com.example.driveservice.service.FileStorageService;
import com.example.driveservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final UserService userService;

    @Override
    public void load(FileLoadRequest request){
        MultipartFile file = request.getFile();
        UUID id = userService.getIdByEmail(request.getEmail());
        String path = createPath(id, request.getPath(), file.getOriginalFilename());
        fileStorageRepository.load(file, path);
    }

    @Override
    public void delete(FileDeleteRequest request) {
        UUID id = userService.getIdByEmail(request.getEmail());
        String path = createPath(id, request.getPath());
        fileStorageRepository.delete(path);
    }

    @Override
    public void rename(FileRenameRequest request) {
        UUID id = userService.getIdByEmail(request.getEmail());
        String oldPath = createPath(id, request.getOldPath());
        String newPath = createPath(id, request.getNewPath());
        fileStorageRepository.rename(oldPath, newPath);
    }

    private String createPath(UUID userId, String path, String filename){
        return createPath(userId, path) + "/" + filename;
    }

    private String createPath(UUID userId, String path){
        return "/user-" + userId.toString() + "-file" + path;
    }


}
