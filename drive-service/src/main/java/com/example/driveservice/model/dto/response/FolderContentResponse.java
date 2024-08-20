package com.example.driveservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FolderContentResponse implements Serializable {
    private String name;
    private String path;
    private String extension;
    private long size;

}
