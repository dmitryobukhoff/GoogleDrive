package com.example.driveservice.controller;

import com.example.driveservice.model.dto.request.FileDeleteRequest;
import com.example.driveservice.model.dto.request.FileDownloadRequest;
import com.example.driveservice.model.dto.request.FileLoadRequest;
import com.example.driveservice.model.dto.request.FileRenameRequest;
import com.example.driveservice.service.FileStorageService;
import com.example.driveservice.util.UserContext;
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
                                  @RequestParam("path") String path){
        FileLoadRequest request = FileLoadRequest.builder()
                .file(file)
                .email(UserContext.getUsername())
                .path(path)
                .build();
        fileService.load(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestBody FileDeleteRequest request){
        request.setEmail(UserContext.getUsername());
        fileService.delete(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/rename")
    public ResponseEntity<?> rename(@RequestBody FileRenameRequest request){
        request.setEmail(UserContext.getUsername());
        fileService.rename(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/download")
    public ResponseEntity<?> download(@RequestBody FileDownloadRequest request){
        request.setEmail(UserContext.getUsername());
        fileService.download(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
