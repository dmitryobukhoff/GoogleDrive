package com.example.driveservice.repository;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageRepository {
    void load(MultipartFile file, String path);
    void delete(String path);
    void rename(String oldPath, String newPath);
}
