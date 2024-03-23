package com.example.markusbot.service;

import com.example.markusbot.enumBot.OrderStatus;
import com.example.markusbot.model.FileEntity;
import com.example.markusbot.model.OrderEntity;
import com.example.markusbot.model.QuestionEntity;
import com.example.markusbot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;

    private final LetterSender letterSender;

    private final OrderRepository orderRepository;

    @Transactional
    public void createNewOrder(Message message, FileEntity file) {
        var order = OrderEntity.builder()
                .author(userService.findByTelegramId(message.getFrom().getId()))
                .photo(file)
                .build();
        orderRepository.save(order);
    }

    @Transactional
    public OrderEntity findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    private OrderEntity getOrderNew(Message message) {
        var user = userService.findByTelegramId(message.getChatId());
        return orderRepository.findFirstByAuthorAndStatusOrderByCreatedAtDesc(user, OrderStatus.NEW).orElseThrow();
    }

    @Transactional
    public void setEuropeSize(Double europeSize, Message message) {
        OrderEntity order = getOrderNew(message);
        order.setSizeEurope(europeSize);
        orderRepository.save(order);
    }

    @Transactional
    public void setSmSize(Double smSize, Message message) {
        OrderEntity order = getOrderNew(message);
        order.setSizeSm(smSize);
        orderRepository.save(order);
    }

    @Transactional
    public void updatePrice(Message message, Double yan, Double rub) {
        OrderEntity order = getOrderNew(message);
        order.setPricePoizon(yan);
        order.setPriceRub(rub);
        orderRepository.save(order);
    }

    @Transactional
    public OrderEntity getOrder(Message message) {
        return getOrderNew(message);
    }

    @Transactional
    public void deleteOrder(Message message) {
        var order = getOrderNew(message);
        order.setStatus(OrderStatus.DELETED);
        orderRepository.save(order);
    }

    @Transactional
    public void setAddress(Message message) {
        var order = getOrderNew(message);
        order.setAddress(message.getText());
        orderRepository.save(order);
    }

    @Transactional
    public void setFio(Message message) {
        var order = getOrderNew(message);
        order.setFio(message.getText());
        orderRepository.save(order);
    }

    @Transactional
    public void setPhoneNumber(Message message) {
        var order = getOrderNew(message);
        order.setPhoneNumber(message.getText());
        orderRepository.save(order);
    }

    @Transactional
    public void setCheck(Message message, FileEntity file) {
        var order = getOrderNew(message);
        order.setCheck(file);
        order.setStatus(OrderStatus.READY);
        letterSender.letterNewOrder(order, userService.getManagers());
        orderRepository.save(order);
    }

    @Transactional
    public Map<Long, String> getOrderMap() {
        return orderRepository.findAllByStatus(OrderStatus.READY).stream()
                .collect(Collectors.toMap(OrderEntity::getId,
                        order -> order.getOrderNumber() + " " + order.getAuthor().getTelegramFirstName() + " " +
                        order.getPriceRub() + " руб"));
    }
}
