package com.example.driveservice.repository;

import com.example.driveservice.model.StorageObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<StorageObject, UUID> {

    @Override
    boolean existsById(UUID uuid);

    @Override
    StorageObject getReferenceById(UUID uuid);
}
