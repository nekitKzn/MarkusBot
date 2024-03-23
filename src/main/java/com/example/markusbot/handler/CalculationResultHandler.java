package com.example.markusbot.handler;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.model.SettingEntity;
import com.example.markusbot.service.SettingService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.example.markusbot.util.TelegramUtil.createButtonByState;

@Component
@RequiredArgsConstructor
public class CalculationResultHandler implements Handler {

    private final SettingService settingService;
    private final UserService userService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.CALCULATION_RESULT;
    }

    @Override
    public Object handle(Message message) {
        if (!NumberUtils.isCreatable(message.getText()) || Double.parseDouble(message.getText()) <= 0) {
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text("Неверный ввод, введите положительное число для того чтобы расчитать стоимость!")
                    .build();
        }

        var setting = settingService.getSetting();

        var result = Double.parseDouble(message.getText()) * setting.getYuanExchangeRate() + setting.getCommission();

        userService.updateUserState(message.getChatId(), StateBot.MAIN);

        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        List.of(createButtonByState(StateBot.CREATE_ORDER)),
                        List.of(
                                InlineKeyboardButton.builder()
                                        .text("↪️ Рассчитать еще")
                                        .callbackData("CALCULATION")
                                        .build(),
                                InlineKeyboardButton.builder()
                                        .text("\uD83D\uDC7D В главное меню")
                                        .callbackData("MAIN")
                                        .build()
                        )))
                .build();

        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(String.format(getCurrentState().getMessage(), result))
                .replyMarkup(replyKeyboard)
                .parseMode("MARKDOWN")
                .build();
    }
}
