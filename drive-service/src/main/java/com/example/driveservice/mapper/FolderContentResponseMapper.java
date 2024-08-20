package com.example.driveservice.mapper;

import com.example.driveservice.model.dto.response.FolderContentResponse;
import com.example.driveservice.util.PathUtil;
import io.minio.messages.Item;;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class FolderContentResponseMapper implements Function<Item, FolderContentResponse> {

    @Override
    public FolderContentResponse apply(Item itemResult) {
        String path = PathUtil.getPath(itemResult.objectName());
        String name = PathUtil.getName(itemResult.objectName());
        String extension = PathUtil.getExtension(itemResult.objectName());
        long size = itemResult.size();
        return FolderContentResponse.builder()
                .path(path)
                .name(name)
                .extension(extension)
                .size(size)
                .build();
    }


}
