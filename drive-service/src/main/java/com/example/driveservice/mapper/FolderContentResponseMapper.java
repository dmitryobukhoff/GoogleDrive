package com.example.driveservice.mapper;

import com.example.driveservice.model.dto.response.FolderContentResponse;
import io.minio.messages.Item;;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class FolderContentResponseMapper implements Function<Item, FolderContentResponse> {

    @Override
    public FolderContentResponse apply(Item itemResult) {
        String path = getPath(itemResult.objectName());
        String name = getName(path);
        String extension = getExtension(itemResult.objectName());
        long size = itemResult.size();
        return FolderContentResponse.builder()
                .path(path)
                .name(name)
                .extension(extension)
                .size(size)
                .build();
    }

    public static String getName(String path){
        String[] route = path.split("/");
        return route[route.length - 1];
    }

    private String getPath(String objectName){
        return objectName.substring(objectName.indexOf("/"), objectName.length() - 1);
    }

    private String getExtension(String path){
        if(isFolder(path)) return "FOLDER";
        String[] route = path.split("\\.");
        return route[route.length - 1];
    }

    private boolean isFolder(String path){
        return path.charAt(path.length() - 1) == '/';
    }
}
