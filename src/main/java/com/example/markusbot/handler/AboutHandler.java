package com.example.markusbot.handler;

import com.example.markusbot.enumBot.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static com.example.markusbot.enumBot.StateBot.MAIN;
import static com.example.markusbot.util.TelegramUtil.createButtonByState;
import static com.example.markusbot.util.TelegramUtil.createButtonWithUrl;

@Component
@RequiredArgsConstructor
public class AboutHandler implements Handler {

    private static final String URL_CANAL = "https://t.me/golden_young21";

    @Override
    public StateBot getCurrentState() {
        return StateBot.ABOUT;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = InlineKeyboardMarkup.builder()
                .keyboard(List.of(List.of(
                                createButtonWithUrl("Наш Канал", MAIN.name(), URL_CANAL)),
                        List.of(createButtonByState(MAIN))))
                .build();

        return getDefaultMessage(message, keyboard);
    }
}
