package com.example.diplom.repository;

import com.example.diplom.entity.CloudFileEntity;
import com.example.diplom.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CloudRepository extends JpaRepository<CloudFileEntity, Integer> {

    Optional<List<CloudFileEntity>> findAllFilesByUser(UserEntity byLogin);

    void removeFileByName(String fileName);

    Optional<CloudFileEntity> findByName(String filename);
}