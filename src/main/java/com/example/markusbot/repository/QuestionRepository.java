package com.example.markusbot.repository;

import com.example.markusbot.model.QuestionEntity;
import com.example.markusbot.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    Optional<QuestionEntity> findFirstByManagerOrderByUpdatedAtDesc(UserEntity author);

    List<QuestionEntity> findAllByAnswerIsNull();

}