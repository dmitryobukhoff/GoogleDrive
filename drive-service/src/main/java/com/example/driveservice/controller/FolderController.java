package com.example.driveservice.controller;

import com.example.driveservice.model.dto.request.FolderContentRequest;
import com.example.driveservice.model.dto.request.FolderDeleteRequest;
import com.example.driveservice.model.dto.request.FolderLoadRequest;
import com.example.driveservice.model.dto.request.FolderRenameRequest;
import com.example.driveservice.model.dto.response.FolderContentResponse;
import com.example.driveservice.service.FolderStorageService;
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
    public ResponseEntity<?> create(@RequestBody FolderLoadRequest request,
                                    @RequestAttribute("username") String username){
        request.setEmail(username);
        fileStorageService.create(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestBody FolderDeleteRequest request,
                                    @RequestAttribute("username") String username){
        request.setEmail(username);
        fileStorageService.delete(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/rename")
    public ResponseEntity<?> rename(@RequestBody FolderRenameRequest request,
                                    @RequestAttribute("username") String username){
        request.setEmail(username);
        fileStorageService.rename(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/content")
    public ResponseEntity<?> content(@RequestParam("path") String path,
                             @RequestAttribute("username") String username){
        List<FolderContentResponse> contents = fileStorageService.getAll(FolderContentRequest.builder()
                        .email(username)
                        .path(path)
                .build());
        return new ResponseEntity<>(Map.of("content", contents), HttpStatus.OK);
    }
}
