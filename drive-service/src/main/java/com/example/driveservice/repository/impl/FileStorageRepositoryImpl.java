package com.example.driveservice.repository.impl;

import com.example.driveservice.exception.MinIOException;
import com.example.driveservice.repository.FileStorageRepository;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Get;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Repository
@RequiredArgsConstructor
public class FileStorageRepositoryImpl implements FileStorageRepository {

    private final MinioClient client;

    @Value("${storage.minio.bucket}")
    private String bucket;

    @Override
    public void load(MultipartFile file, String path){
        try{
            client.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(path)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );
        }catch (Exception exception){
            throw new MinIOException(exception.getMessage());
        }

    }

    @Override
    public void delete(String path) {
        try {
            client.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(path)
                            .build()
            );
        }catch(Exception exception){
            throw new MinIOException(exception.getMessage());
        }
    }

    @Override
    public void rename(String oldPath, String newPath){
        copy(oldPath, newPath);
        delete(oldPath);
    }

    private boolean exist(String oldPath){
        try{
            client.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucket)
                            .object(oldPath)
                            .build()
            );
            return true;
        }catch (Exception exception){
            throw new MinIOException(exception.getMessage());
        }
    }

    @Override
    public InputStream download(String path) {
        try{
            return client.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(path)
                            .build()
            );
        }catch (Exception exception){
            throw new MinIOException(exception.getMessage());
        }
    }

    private void copy(String oldPath, String newPath){
        try{
            client.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(bucket)
                            .object(newPath)
                            .source(CopySource.builder()
                                    .bucket(bucket)
                                    .object(oldPath)
                                    .build())
                            .build()
            );
        }catch (Exception exception) {
            throw new MinIOException(exception.getMessage());
        }
    }
}
