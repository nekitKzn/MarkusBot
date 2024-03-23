package com.example.markusbot.handler;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static com.example.markusbot.util.TelegramUtil.createButtonByState;

@Component
@RequiredArgsConstructor
public class MainHandler implements Handler {

    private final SettingService settingService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.MAIN;
    }

    @Override
    public Object handle(Message message) {

        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        List.of(createButtonByState(StateBot.CALCULATION)),
                        List.of(createButtonByState(StateBot.CREATE_ORDER)),
                        List.of(createButtonByState(StateBot.CREATE_QUESTION)),
                        List.of(
                                createButtonByState(StateBot.ABOUT),
                                createButtonByState(StateBot.FEEDBACK)
                        )))
                .build();
        var setting = settingService.getSetting();

        var input = new InputMediaPhoto(setting.getFile().getFileId());

        var input2 = new InputMediaPhoto(setting.getFile().getFileId());
        input2.setCaption("nikita2");

        return SendPhoto.builder()
                .chatId(message.getChatId())
                .photo(new InputFile(setting.getFile().getFileId()))
                .caption(getCurrentState().getMessage())
                .replyMarkup(replyKeyboard)
                .build();
    }
}
