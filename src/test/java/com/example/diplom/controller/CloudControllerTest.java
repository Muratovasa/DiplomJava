package com.example.diplom.controller;

import com.example.diplom.dto.CloudFileDto;
import com.example.diplom.entity.CloudFileEntity;
import com.example.diplom.entity.UserEntity;
import com.example.diplom.repository.CloudRepository;
import com.example.diplom.repository.UsersRepository;
import com.example.diplom.service.fileService.FileServiceImpl;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CloudControllerTest {
    @ExtendWith(MockitoExtension.class)

    @Mock
    CloudRepository filesRepository;
    @Mock
    UsersRepository userRepository;
    @InjectMocks
    FileServiceImpl fileService;
    private final CloudFileEntity file = new CloudFileEntity();
    private final List<CloudFileEntity> fileList = new ArrayList<>();
    private final UserEntity user = UserEntity.builder().login("user").password("user").build();
    private final String FILENAME = "file";

    @BeforeEach
    void setUp() {
        System.out.println("Начало теста");
    }

    @AfterAll
    static void finish() {
        System.out.println("Finish");
    }

    @Test
    void getFileTest() throws ValidationException {
        file.setFileName(FILENAME);
        given(filesRepository.findByName(FILENAME)).willReturn(Optional.of(file));
        CloudFileEntity newFile = fileService.getFile(FILENAME);
        Assertions.assertEquals(file.getFileName(), newFile.getFileName());
    }

    @Test
    void deleteFileTest() {
        fileService.delete(FILENAME);
        verify(filesRepository, times(1)).removeFileByName(FILENAME);
    }

    @Test
    void updateFileNameTest() throws ValidationException {
        final String NEWFILENAME = "newFileName";
        given(filesRepository.findByName(FILENAME)).willReturn(Optional.of(file));
        fileService.updateFileName(FILENAME, NEWFILENAME);
        verify(filesRepository, times(1)).save(file);
    }

    @Test
    void getAllFileTest() {
        int limit = 1;
        file.setFileName(FILENAME);
        fileList.add(file);
        given(filesRepository.findAllFilesByUser(user)).willReturn(Optional.of(fileList));
        given(userRepository.findByLogin(user.getLogin())).willReturn(user);
        List<CloudFileDto> responseList = fileService.show(user.getLogin(), limit);
        Assertions.assertEquals(responseList.get(0).getFileName(), file.getFileName());
    }
}