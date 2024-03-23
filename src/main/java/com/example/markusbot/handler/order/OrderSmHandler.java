package com.example.markusbot.handler.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.OrderService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class OrderSmHandler implements Handler {

    private final OrderService orderService;
    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_SM;
    }

    @Override
    public Object handle(Message message) {
        if (!NumberUtils.isCreatable(message.getText()) || Double.parseDouble(message.getText()) <= 0) {
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text("Неверный ввод, введите положительное число для того чтобы указань размер по Европейской шкале")
                    .build();
        }
        orderService.setEuropeSize(Double.parseDouble(message.getText()), message);
        userService.updateUserState(message.getChatId(), StateBot.ORDER_PRICE);
        return getSimpleMessage(message, null);
    }
}
