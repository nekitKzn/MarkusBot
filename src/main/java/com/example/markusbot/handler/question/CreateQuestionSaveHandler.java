package com.example.markusbot.handler.question;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.QuestionService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CreateQuestionSaveHandler implements Handler {

    private final QuestionService questionService;
    private final UserService userService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_QUESTION_SAVE;
    }

    @Override
    public Object handle(Message message) {
        if (Objects.isNull(message.getText())) {
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text("Неверный ввод, напишите ваш вопрос!")
                    .build();
        }
        questionService.saveNewQuestion(message);
        userService.updateUserState(message.getChatId(), StateBot.MAIN);
        var keyboard = getKeyboardDefault(StateBot.MAIN);
        return getSimpleMessage(message, keyboard);
    }
}
