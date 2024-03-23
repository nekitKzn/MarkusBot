package com.example.markusbot.handler.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.example.markusbot.enumBot.StateBot.*;

@Component
@RequiredArgsConstructor
public class CreateOrderHandler implements Handler {

    private final UserService userService;


    @Override
    public StateBot getCurrentState() {
        return CREATE_ORDER;
    }

    @Override
    public StateBot getNextState() {
        return ORDER_EUROPE;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardDefault(MAIN);
        return getSimpleMessage(message, keyboard);
    }
}
