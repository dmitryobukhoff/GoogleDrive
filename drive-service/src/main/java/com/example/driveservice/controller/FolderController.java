package com.example.driveservice.controller;

import com.example.driveservice.model.dto.request.FolderDeleteRequest;
import com.example.driveservice.model.dto.request.FolderLoadRequest;
import com.example.driveservice.model.dto.request.FolderRenameRequest;
import com.example.driveservice.service.FolderStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drive/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderStorageService fileStorageService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody FolderLoadRequest request){
        fileStorageService.create(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestBody FolderDeleteRequest request){
        fileStorageService.delete(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/rename")
    public ResponseEntity<?> rename(@RequestBody FolderRenameRequest request){
        fileStorageService.rename(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
