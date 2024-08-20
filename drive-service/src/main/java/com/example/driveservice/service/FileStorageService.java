package com.example.driveservice.service;

import com.example.driveservice.model.dto.request.FileDeleteRequest;
import com.example.driveservice.model.dto.request.FileDownloadRequest;
import com.example.driveservice.model.dto.request.FileLoadRequest;
import com.example.driveservice.model.dto.request.FileRenameRequest;
import com.example.driveservice.model.dto.response.FileDownloadResponse;
import org.springframework.core.io.ByteArrayResource;

public interface FileStorageService {
    void load(FileLoadRequest request);
    void delete(FileDeleteRequest request);
    void rename(FileRenameRequest request);
    FileDownloadResponse download(FileDownloadRequest request);
}
