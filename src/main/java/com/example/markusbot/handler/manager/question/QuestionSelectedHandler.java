package com.example.markusbot.handler.manager.question;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.model.QuestionEntity;
import com.example.markusbot.service.QuestionService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuestionSelectedHandler implements Handler {

    private final QuestionService questionService;
    private final UserService userService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.QUESTION_SELECTED;
    }

    @Override
    public Object handle(Message message) {
        var question = questionService.findById(Long.valueOf(message.getText()));
        logSelectedButton(message, question);
        questionService.selectQuestion(question, message.getChatId());
        userService.updateUserState(message.getChatId(), StateBot.ANSWER_SEND);
        return getDefaultMessage(message, null, question.getText());
    }

    private static void logSelectedButton(Message message, QuestionEntity question) {
        log.info("User @{}:{} select button: {}", message.getChat().getUserName(),
                message.getChat().getFirstName(), question.getText());
    }
}
