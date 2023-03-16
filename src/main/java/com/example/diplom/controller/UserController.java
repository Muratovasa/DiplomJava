package com.example.diplom.controller;

import com.example.diplom.dto.AuthRS;
import com.example.diplom.dto.UserDTO;
import com.example.diplom.security.JWTToken;
import com.example.diplom.service.UserService;
import com.example.diplom.service.authService.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class UserController {
    private final AuthService authService;
    private final JWTToken jwtToken;

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> createToken(@RequestBody UserDTO userDTO) {
        final UserDetails userDetails = userService.getUserByLogin(userDTO.getLogin());
        if (userDetails != null){
            var name = userDetails.getUsername();
            var password = userDetails.getPassword();
            if (name.equals(userDTO.getLogin()) && password.equals(userDTO.getPassword())){
                final String token = jwtToken.generateToken(userDetails);
                userService.addTokenToUser(userDTO.getLogin(), token);
                return ResponseEntity.status(HttpStatus.OK).body(AuthRS.builder().authToken(token).build());
            }
        }
        return ResponseEntity.status(400).body("Bad credentials");
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        authService.logoutUser(authToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}