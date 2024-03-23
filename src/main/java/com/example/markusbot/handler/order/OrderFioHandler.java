package com.example.markusbot.handler.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class OrderFioHandler implements Handler {

    private final UserService userService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_FIO;
    }

    @Override
    public Object handle(Message message) {
        userService.updateUserState(message.getChatId(), StateBot.ORDER_ADDRESS);
        return getSimpleMessage(message, null);
    }
}
