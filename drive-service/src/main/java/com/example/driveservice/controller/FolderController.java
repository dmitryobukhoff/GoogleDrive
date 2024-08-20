package com.example.driveservice.controller;

import com.example.driveservice.model.dto.request.FolderContentRequest;
import com.example.driveservice.model.dto.request.FolderDeleteRequest;
import com.example.driveservice.model.dto.request.FolderLoadRequest;
import com.example.driveservice.model.dto.request.FolderRenameRequest;
import com.example.driveservice.model.dto.response.FolderContentResponse;
import com.example.driveservice.service.FolderStorageService;
import com.example.driveservice.util.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/drive/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderStorageService fileStorageService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody FolderLoadRequest request){
        request.setEmail(UserContext.getUsername());
        fileStorageService.create(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestBody FolderDeleteRequest request){
        request.setEmail(UserContext.getUsername());
        fileStorageService.delete(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/rename")
    public ResponseEntity<?> rename(@RequestBody FolderRenameRequest request){
        request.setEmail(UserContext.getUsername());
        fileStorageService.rename(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/content")
    public ResponseEntity<?> content(@RequestParam("path") String path){
        List<FolderContentResponse> contents = fileStorageService.getAll(FolderContentRequest.builder()
                        .path(path)
                        .email(UserContext.getUsername())
                .build());
        return new ResponseEntity<>(Map.of("content", contents), HttpStatus.OK);
    }
}
