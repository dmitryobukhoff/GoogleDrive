package com.example.driveservice.service.impl;

import com.example.driveservice.exception.DownloadFileException;
import com.example.driveservice.mapper.FolderContentResponseMapper;
import com.example.driveservice.model.dto.request.FileDeleteRequest;
import com.example.driveservice.model.dto.request.FileDownloadRequest;
import com.example.driveservice.model.dto.request.FileLoadRequest;
import com.example.driveservice.model.dto.request.FileRenameRequest;
import com.example.driveservice.model.dto.response.FileDownloadResponse;
import com.example.driveservice.repository.FileStorageRepository;
import com.example.driveservice.service.FileStorageService;
import com.example.driveservice.service.UserService;
import com.example.driveservice.util.PathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final UserService userService;

    @Override
    public void load(FileLoadRequest request){
        MultipartFile file = request.getFile();
        UUID id = userService.getIdByEmail(request.getEmail());
        String path = createPath(id, request.getPath(), file.getOriginalFilename());
        fileStorageRepository.load(file, path);
        log.info("User: {} load file {} on drive", request.getEmail(), path);
    }

    @Override
    @CacheEvict(value = "file_cache", key = "#request.email + ':' + #request.path")
    public void delete(FileDeleteRequest request) {
        UUID id = userService.getIdByEmail(request.getEmail());
        String path = createPath(id, request.getPath());
        fileStorageRepository.delete(path);
        log.info("User: {} delete {} file", request.getEmail(), path);
    }

    @Override
    public void rename(FileRenameRequest request) {
        UUID id = userService.getIdByEmail(request.getEmail());
        String oldPath = createPath(id, request.getOldPath());
        String newPath = createPath(id, request.getNewPath());
        fileStorageRepository.rename(oldPath, newPath);
        log.info("User: {} rename file from {} to {} path", request.getEmail(), oldPath, newPath);
    }

    @Override
    @Cacheable(value = "file_cache", key = "#request.email + ':' + #request.path")
    public FileDownloadResponse download(FileDownloadRequest request) {
        UUID id = userService.getIdByEmail(request.getEmail());
        String path = createPath(id, request.getPath());
        try {
            InputStream inputStream = fileStorageRepository.download(path);
            log.info("User: {} download file {}", request.getEmail(), path);
            String name = PathUtil.getName(path);
            return FileDownloadResponse.builder()
                    .name(name)
                    .file(inputStream.readAllBytes())
                    .build();
        } catch (Exception exception) {
            log.error("User: {} download file {} with error", request.getEmail(), path);
            throw new DownloadFileException(exception.getMessage());
        }
    }

    private String createPath(UUID userId, String path, String filename){
        return createPath(userId, path) + "/" + filename;
    }

    private String createPath(UUID userId, String path){
        return "/user-" + userId.toString() + "-file" + path;
    }


}
