package com.example.markusbot.handler.manager.question;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.markusbot.util.TelegramUtil.button;
import static com.example.markusbot.util.TelegramUtil.createButtonByState;

@Component
@RequiredArgsConstructor
public class QuestionListHandler implements Handler {

    private final static String NOT_FOUND_QUESTION = "Вопросов к сожалению нет \uD83D\uDE09";
    private final static String SUCCESS_FIND_QUESTION = "Чтобы ответить на вопрос, нажмите на него и напишите ответ, он сразу уйдет отправителю!";

    private final QuestionService questionService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.QUESTION_LIST;
    }

    @Override
    public StateBot getNextState() {
        return StateBot.QUESTION_SELECTED;
    }

    @Override
    public Object handle(Message message) {
        Map<Long, String> listQuestion = questionService.getQuestionMap();

        List<List<InlineKeyboardButton>> lists = new ArrayList<>(listQuestion.entrySet().stream()
                .map(entry -> button(entry.getValue(), entry.getKey()))
                .map(List::of).toList());
        lists.add(List.of(createButtonByState(StateBot.MANAGER_MAIN)));
        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(lists).build();

        if (listQuestion.isEmpty()) {
            return getDefaultMessage(message, replyKeyboard, NOT_FOUND_QUESTION);
        } else {
            return getDefaultMessage(message, replyKeyboard, SUCCESS_FIND_QUESTION);
        }
    }
}
