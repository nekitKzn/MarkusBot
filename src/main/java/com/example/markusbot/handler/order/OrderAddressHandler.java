package com.example.markusbot.handler.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.OrderService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class OrderAddressHandler implements Handler {

    private final OrderService orderService;
    private final UserService userService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_ADDRESS;
    }

    @Override
    public Object handle(Message message) {
        orderService.setFio(message);
        userService.updateUserState(message.getChatId(), StateBot.ORDER_PHONE_NUMBER);
        return getSimpleMessage(message, null);
    }
}
