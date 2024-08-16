package com.example.driveservice.repository;


import com.example.driveservice.model.dto.response.FolderContentResponse;

import java.util.List;

public interface FolderStorageRepository {
    void create(String path);
    void delete(String path);
    void rename(String oldPath, String newPath);
    List<FolderContentResponse> getAll(String path);
}
