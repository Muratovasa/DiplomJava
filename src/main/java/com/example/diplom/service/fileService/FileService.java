package com.example.diplom.service.fileService;

import com.example.diplom.dto.CloudFileDto;
import com.example.diplom.entity.CloudFileEntity;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FileService {
    void upload(String file, MultipartFile files) throws IOException;

    List<CloudFileDto> show(String login, int limit);

    void delete(String fileName);

    CloudFileEntity getFile(String filename) throws ValidationException;

    CloudFileEntity updateFileName(String fileName, String newName) throws ValidationException;
}
