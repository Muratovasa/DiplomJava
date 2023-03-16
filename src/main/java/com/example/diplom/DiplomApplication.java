package com.example.diplom;

import com.example.diplom.entity.UserEntity;
import com.example.diplom.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DiplomApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(UsersRepository usersRepository) {
        return args -> usersRepository.save(new UserEntity(1,"user",null,"user"));
    }

}
