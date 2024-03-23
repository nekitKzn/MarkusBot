package com.example.markusbot.handler.manager.commission;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class ChangeCommissionHandler implements Handler {

    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CHANGE_COMMISSION;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardDefault(StateBot.MANAGER_MAIN);
        userService.updateUserState(message.getChatId(), StateBot.SAVE_COMMISSION);
        return getDefaultMessage(message, keyboard);
    }
}
