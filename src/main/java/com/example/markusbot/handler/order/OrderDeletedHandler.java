package com.example.markusbot.handler.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.handler.MainHandler;
import com.example.markusbot.service.OrderService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class OrderDeletedHandler implements Handler {

    private final OrderService orderService;
    private final UserService userService;

    private final MainHandler mainHandler;


    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_DELETED;
    }

    @Override
    public Object handle(Message message) {
        orderService.deleteOrder(message);
        userService.updateUserState(message.getChatId(), StateBot.MAIN);
        return mainHandler.handle(message);
    }
}
