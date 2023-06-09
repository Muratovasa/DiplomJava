package com.example.diplom.controller;

import com.example.diplom.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppIntegrationTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final String LOGIN_PATH = "/login";
    private final String LOGOUT_PATH = "/logout";
    private final String LOGIN = "user";
    private final String BAD_LOGIN = "admin";
    private final String PASSWORD = "user";
    private final String ANOTHER_PATH = "/file";

    @Test
    void userIsUnauthorized() throws Exception {
        mvc.perform(get(ANOTHER_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginUserUnauthenticated() throws Exception {
        UserDTO authRequest = new UserDTO(BAD_LOGIN, PASSWORD);
        mvc.perform(post(LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginUserAuthenticated() throws Exception {
        UserDTO authRequest = new UserDTO(LOGIN, PASSWORD);
        mvc.perform(post(LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void logoutUserTest() throws Exception {
        UserDTO authRequest = new UserDTO(LOGIN, PASSWORD);
        mvc.perform(post(LOGOUT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().is3xxRedirection());
    }
}
