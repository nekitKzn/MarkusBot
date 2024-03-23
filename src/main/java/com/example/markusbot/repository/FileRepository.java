package com.example.markusbot.repository;

import com.example.markusbot.model.FileEntity;
import com.example.markusbot.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findFirstByAuthorOrderByCreatedAtDesc(UserEntity user);
}
