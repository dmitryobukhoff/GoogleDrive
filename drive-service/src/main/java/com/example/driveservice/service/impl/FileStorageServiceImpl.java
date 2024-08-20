package com.example.driveservice.service.impl;

import com.example.driveservice.mapper.FolderContentResponseMapper;
import com.example.driveservice.model.dto.request.FileDeleteRequest;
import com.example.driveservice.model.dto.request.FileDownloadRequest;
import com.example.driveservice.model.dto.request.FileLoadRequest;
import com.example.driveservice.model.dto.request.FileRenameRequest;
import com.example.driveservice.repository.FileStorageRepository;
import com.example.driveservice.service.FileStorageService;
import com.example.driveservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
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
    public void download(FileDownloadRequest request) {
        UUID id = userService.getIdByEmail(request.getEmail());
        String drivePath = createPath(id, request.getDrivePath());
        String localPath = request.getLocalPath() + "\\" + FolderContentResponseMapper.getName(drivePath);
        fileStorageRepository.download(drivePath, localPath);
        log.info("User: {} download file {} to local directory {}", request.getEmail(), drivePath, localPath);
    }

    private String createPath(UUID userId, String path, String filename){
        return createPath(userId, path) + "/" + filename;
    }

    private String createPath(UUID userId, String path){
        return "/user-" + userId.toString() + "-file" + path;
    }


}
