package com.example.markusbot.handler.manager.yuan;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class ChangeYuanHandler implements Handler {

    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CHANGE_YUAN;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardDefault(StateBot.MANAGER_MAIN);
        userService.updateUserState(message.getChatId(), StateBot.SAVE_YUAN);
        return getDefaultMessage(message, keyboard);
    }
}
