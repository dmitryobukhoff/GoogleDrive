package com.example.driveservice.repository;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorageRepository {
    void load(MultipartFile file, String path);
    void delete(String path);
    void rename(String oldPath, String newPath);
    void download(String path, String filename);
}
