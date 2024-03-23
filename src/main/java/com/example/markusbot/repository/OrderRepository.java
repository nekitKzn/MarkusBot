package com.example.markusbot.repository;

import com.example.markusbot.enumBot.OrderStatus;
import com.example.markusbot.model.OrderEntity;
import com.example.markusbot.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findFirstByAuthorAndStatusOrderByCreatedAtDesc(UserEntity author, OrderStatus status);

    List<OrderEntity> findAllByStatus(OrderStatus status);
}
