package com.example.driveservice.service;

import com.example.driveservice.model.dto.request.FolderDeleteRequest;
import com.example.driveservice.model.dto.request.FolderLoadRequest;
import com.example.driveservice.model.dto.request.FolderRenameRequest;

public interface FolderStorageService {

    void create(FolderLoadRequest request);
    void delete(FolderDeleteRequest request);

    void rename(FolderRenameRequest request);
}
