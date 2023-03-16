package com.example.diplom.service.authService;

import com.example.diplom.repository.UsersRepository;
import com.example.diplom.service.UserService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Getter
public class AuthServiceImpl implements AuthService {
    private final Map<String, String> tokenRepository = new HashMap<>();
    Random random = new Random();
    UsersRepository userRepository;

    final UserService userService;

    public AuthServiceImpl(UsersRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void logoutUser(String token) {
        userRepository.removeToken(token);
    }

}
