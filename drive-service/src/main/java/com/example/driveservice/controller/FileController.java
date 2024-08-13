package com.example.driveservice.controller;

import com.example.driveservice.model.dto.request.FileDeleteRequest;
import com.example.driveservice.model.dto.request.FileLoadRequest;
import com.example.driveservice.model.dto.request.FileRenameRequest;
import com.example.driveservice.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/v1/drive/file")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileService;

    @PostMapping(value = "/load")
    public ResponseEntity<?> load(@RequestParam("file") MultipartFile file,
                                  @RequestParam("email") String email,
                                  @RequestParam("path") String path){
        FileLoadRequest request = FileLoadRequest.builder()
                .file(file)
                .email(email)
                .path(path)
                .build();
        fileService.load(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestBody FileDeleteRequest request){
        fileService.delete(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/rename")
    public ResponseEntity<?> rename(@RequestBody FileRenameRequest request){
        fileService.rename(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
