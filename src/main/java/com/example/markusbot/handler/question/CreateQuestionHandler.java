package com.example.markusbot.handler.question;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class CreateQuestionHandler implements Handler {

    private final UserService userService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_QUESTION;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardDefault(StateBot.MAIN);
        userService.updateUserState(message.getChatId(), StateBot.CREATE_QUESTION_SAVE);

        return getDefaultMessage(message, keyboard);
    }
}
