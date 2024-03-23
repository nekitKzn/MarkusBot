package com.example.markusbot.handler.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.FileService;
import com.example.markusbot.service.OrderService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class OrderPaySuccessHandler implements Handler {

    private final FileService fileService;

    private final UserService userService;
    private final OrderService orderService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_PAY_SUCCESS;
    }

    @Override
    public Object handle(Message message) {
        var file = fileService.createNewPhoto(message);
        orderService.setCheck(message, file);
        userService.updateUserState(message.getChatId(), StateBot.MAIN);
        var keyboard = getKeyboardDefault(StateBot.MAIN);
        return getSimpleMessage(message, keyboard);
    }
}
