package com.example.driveservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FolderContentResponse {
    private String name;
    private String path;
    private String extension;
    private long size;
}
