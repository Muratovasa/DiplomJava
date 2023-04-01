package com.example.diplom.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@ToString
@Table(name = "files")
public class CloudFileEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "file_name", length = 100, nullable = false)
    private String fileName;

    @Column(name = "file_size")
    private Long size;

    @Column(nullable = false, name = "upload_date")
    LocalDate uploadDate;

    @Column(name = "content")
    private byte [] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
}