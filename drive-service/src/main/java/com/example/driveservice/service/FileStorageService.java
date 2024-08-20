package com.example.driveservice.service;

import com.example.driveservice.model.dto.request.FileDeleteRequest;
import com.example.driveservice.model.dto.request.FileDownloadRequest;
import com.example.driveservice.model.dto.request.FileLoadRequest;
import com.example.driveservice.model.dto.request.FileRenameRequest;

public interface FileStorageService {
    void load(FileLoadRequest request);
    void delete(FileDeleteRequest request);
    void rename(FileRenameRequest request);
    void download(FileDownloadRequest request);
}
