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
public class OrderPhoneNumberHandler implements Handler {

    private final OrderService orderService;
    private final UserService userService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_PHONE_NUMBER;
    }

    @Override
    public Object handle(Message message) {
        orderService.setAddress(message);
        userService.updateUserState(message.getChatId(), StateBot.ORDER_PHONE_NUMBER_SAVE);
        return getSimpleMessage(message, null);
    }
}
