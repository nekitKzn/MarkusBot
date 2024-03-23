package com.example.markusbot.handler.manager;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.model.SettingEntity;
import com.example.markusbot.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static com.example.markusbot.util.TelegramUtil.createButtonByState;

@Component
@RequiredArgsConstructor
public class ManagerMainHandler implements Handler {

    private final SettingService settingService;
    @Override
    public StateBot getCurrentState() {
        return StateBot.MANAGER_MAIN;
    }

    @Override
    public Object handle(Message message) {
        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        List.of(createButtonByState(StateBot.ORDER_LIST)),
                        List.of(createButtonByState(StateBot.QUESTION_LIST)),
                        List.of(
                                createButtonByState(StateBot.CHANGE_YUAN),
                                createButtonByState(StateBot.CHANGE_COMMISSION)
                        ),
                        List.of(createButtonByState(StateBot.CHANGE_MENU)),
                        List.of(createButtonByState(StateBot.MAIN))
                ))
                .build();

        SettingEntity settingEntity = settingService.getSetting();
        return getDefaultMessage(message, replyKeyboard, settingEntity.getYuanExchangeRate(), settingEntity.getCommission());
    }
}
