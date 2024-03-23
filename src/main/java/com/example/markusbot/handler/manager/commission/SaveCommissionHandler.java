package com.example.markusbot.handler.manager.commission;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.SettingService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class SaveCommissionHandler implements Handler {

    private final SettingService settingService;
    private final UserService userService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.SAVE_COMMISSION;
    }

    @Override
    public Object handle(Message message) {
        if (!NumberUtils.isCreatable(message.getText()) || Double.parseDouble(message.getText()) <= 0) {
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text("Неверный ввод, введите положительное число для того чтобы поменять комиссию!")
                    .build();
        }
        settingService.updateCommission(Double.valueOf(message.getText()));
        userService.updateUserState(message.getChatId(), StateBot.MANAGER_MAIN);
        var keyboard = getKeyboardDefault(StateBot.MANAGER_MAIN);
        return getDefaultMessage(message, keyboard);
    }
}
