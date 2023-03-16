package com.example.diplom.service.fileService;

import com.example.diplom.dto.CloudFileDto;
import com.example.diplom.entity.CloudFileEntity;
import com.example.diplom.repository.CloudRepository;
import com.example.diplom.repository.UsersRepository;
import com.example.diplom.service.authService.AuthServiceImpl;
import jakarta.xml.bind.ValidationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    final
    AuthServiceImpl authService;
    final
    CloudRepository filesRepository;
    final
    UsersRepository userRepository;

    public FileServiceImpl(CloudRepository filesRepository, AuthServiceImpl authService, UsersRepository userRepository) {
        this.filesRepository = filesRepository;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @Override
    public void upload(String fileName, MultipartFile files) throws IOException {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        CloudFileEntity file = CloudFileEntity.builder().fileName(fileName).data(files.getBytes()).size(files.getSize())
                .userEntity(userRepository.findByLogin(login)).uploadDate(LocalDate.now()).build();

        filesRepository.save(file);
    }

    @Override
    public List<CloudFileDto> show(String login, int limit) {
        Optional<List<CloudFileEntity>> fileList = filesRepository.findAllFilesByUser(userRepository.findByLogin(login));
        return fileList.get().stream().map(f -> new CloudFileDto(f.getFileName(), f.getSize()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String fileName) {
        filesRepository.removeFileByName(fileName);
    }

    @Override
    public CloudFileEntity getFile(String filename) throws ValidationException {
        Optional<CloudFileEntity> optionalFile = filesRepository.findByName(filename);
        if (optionalFile.isEmpty()) {
            throw new ValidationException("Файла с таким именем не существует");
        }
        return optionalFile.get();
    }

    @Override
    public CloudFileEntity updateFileName(String fileName, String newName) throws ValidationException {
        Optional<CloudFileEntity> optionalFile = filesRepository.findByName(fileName);
        if (optionalFile.isEmpty()) {
            throw new ValidationException("Файла с таким именем не существует");
        }
        CloudFileEntity file = optionalFile.get();
        file.setFileName(newName);
        filesRepository.save(file);
        return file;
    }
}