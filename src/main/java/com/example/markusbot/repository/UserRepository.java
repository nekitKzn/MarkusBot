package com.example.markusbot.repository;

import com.example.markusbot.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByTelegramUserName(String userName);

    Optional<UserEntity> findByTelegramId(Long telegramId);

    boolean existsByTelegramIdAndAdminIsTrue(Long telegramId);

    boolean existsByTelegramIdAndManagerIsTrue(Long telegramId);

    List<UserEntity> findAllByManager(Boolean manager);

}
