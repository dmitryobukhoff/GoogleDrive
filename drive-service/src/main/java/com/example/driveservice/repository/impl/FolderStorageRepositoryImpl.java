package com.example.driveservice.repository.impl;

import com.example.driveservice.exception.MinIOException;
import com.example.driveservice.model.dto.response.FolderContentResponse;
import com.example.driveservice.repository.FolderStorageRepository;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


@Repository
@RequiredArgsConstructor
public class FolderStorageRepositoryImpl implements FolderStorageRepository {

    private final MinioClient client;
    private final Function<Item, FolderContentResponse> mapper;

    @Value("${storage.minio.bucket}")
    private String bucket;

    @Override
    public void create(String path){
        try {
            client.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(path)
                        .stream(new ByteArrayInputStream(new byte[0]), 0, -1)
                        .build()
            );
        } catch (Exception exception){
            throw new MinIOException(exception.getMessage());
        }
    }

    @Override
    public void delete(String path) {
        try {
            Iterable<Result<Item>> objects = getObjects(path, true);
            for(Result<Item> object : objects){
                client.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucket)
                                .object(object.get().objectName())
                                .build()
                );
            }

        }catch (Exception exception){
            throw new MinIOException(exception.getMessage());
        }

    }

    @Override
    public void rename(String oldPath, String newPath) {
        copy(oldPath, newPath);
        delete(oldPath);
    }

    @Override
    public List<FolderContentResponse> getAll(String path) {
        Iterable<Result<Item>> results = getObjects(path, false);
        List<FolderContentResponse> responses = new ArrayList<>();
        try {
            for(Result<Item> result : results){
                Item item = result.get();
                if(!item.objectName().equals(path))
                    responses.add(mapper.apply(item));
            }
            return responses;
        }catch (Exception exception){
            throw new DataAccessResourceFailureException(exception.getMessage());
        }
    }

    private void copy(String oldPath, String newPath){
        try{
            Iterable<Result<Item>> objects = getObjects(oldPath, true);
            for (Result<Item> object : objects) {
                Item item = object.get();
                String oldPrefix = item.objectName();
                String newPrefix = oldPrefix.replace(oldPath, newPath);
                client.copyObject(
                        CopyObjectArgs.builder()
                                .bucket(bucket)
                                .object(newPrefix)
                                .source(
                                        CopySource.builder()
                                                .bucket(bucket)
                                                .object(oldPrefix)
                                                .build()
                                )
                                .build()
                );
            }
        }catch (Exception exception){
            throw new MinIOException(exception.getMessage());
        }

    }

    private Iterable<Result<Item>> getObjects(String path, boolean recursive){
        return client.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucket)
                        .prefix(path)
                        .recursive(recursive)
                        .build()
        );
    }
}
