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
public class OrderPhoneNumberSaveHandler implements Handler {

    private final OrderService orderService;
    private final UserService userService;
    private final OrderPayHandler orderPayHandler;


    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_PHONE_NUMBER_SAVE;
    }

    @Override
    public Object handle(Message message) {
        orderService.setPhoneNumber(message);
        userService.updateUserState(message.getChatId(), StateBot.ORDER_PAY);
        return orderPayHandler.handle(message);
    }
}
