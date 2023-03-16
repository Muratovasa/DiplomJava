package com.example.diplom.controller;

import com.example.diplom.entity.CloudFileEntity;
import com.example.diplom.service.UserService;
import com.example.diplom.service.authService.AuthService;
import com.example.diplom.service.fileService.FileService;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CloudController {
    private final FileService fileService;
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@NotNull @RequestBody MultipartFile multipartFile,
                                           @RequestParam("filename") String fileName) throws IOException {
        fileService.upload(fileName, multipartFile);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestParam("filename") String filename) {
       fileService.delete(filename);
       return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestParam ("filename") String filename) throws ValidationException {
        CloudFileEntity file = fileService.getFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +file.getFileName() + "\"")
                .body(file.getData());
    }

    @PutMapping("/file")
    public ResponseEntity<?> updateFileName(@RequestParam("filename") String filename, @RequestBody Map<String, String> newFileName) throws ValidationException {
      fileService.updateFileName(filename,newFileName.get("filename"));
      return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/list")
    public Object getAllFile(@RequestParam("limit") int limit) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return fileService.show(login, limit);
    }
}