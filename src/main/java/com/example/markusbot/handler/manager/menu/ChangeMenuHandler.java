package com.example.markusbot.handler.manager.menu;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class ChangeMenuHandler implements Handler {

    private final UserService userService;



    @Override
    public StateBot getCurrentState() {
        return StateBot.CHANGE_MENU;
    }

    @Override
    public StateBot getNextState() {
        return StateBot.SAVE_MENU;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardDefault(StateBot.MANAGER_MAIN);
        return getDefaultMessage(message, keyboard);
    }
}
