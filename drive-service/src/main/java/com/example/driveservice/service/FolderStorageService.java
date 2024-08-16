package com.example.driveservice.service;

import com.example.driveservice.model.dto.request.FolderContentRequest;
import com.example.driveservice.model.dto.request.FolderDeleteRequest;
import com.example.driveservice.model.dto.request.FolderLoadRequest;
import com.example.driveservice.model.dto.request.FolderRenameRequest;
import com.example.driveservice.model.dto.response.FolderContentResponse;

import java.util.List;

public interface FolderStorageService {

    void create(FolderLoadRequest request);
    void delete(FolderDeleteRequest request);
    void rename(FolderRenameRequest request);
    List<FolderContentResponse> getAll(FolderContentRequest request);
}
