package com.example.driveservice.repository;


public interface FolderStorageRepository {
    void create(String path);
    void delete(String path);
    void rename(String oldPath, String newPath);
    void getAll(String path);
}
