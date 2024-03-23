package com.example.markusbot.handler.manager.question;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.QuestionService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class AnswerSendHandler implements Handler {

    private final UserService userService;
    private final QuestionService questionService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.ANSWER_SEND;
    }

    @Override
    public Object handle(Message message) {
        var manager = userService.findByTelegramId(message.getFrom().getId());
        questionService.saveAnswerSelectedQuestion(manager, message.getText());
        var keyboard = getKeyboardDefault(StateBot.MANAGER_MAIN);
        userService.updateUserState(message.getChatId(), StateBot.MANAGER_MAIN);
        return getDefaultMessage(message, keyboard);
    }
}
