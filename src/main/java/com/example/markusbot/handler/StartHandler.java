package com.example.markusbot.handler;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.model.QuestionEntity;
import com.example.markusbot.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Objects;

import static com.example.markusbot.util.TelegramUtil.createButtonByState;


@Component
@Slf4j
@RequiredArgsConstructor
public class StartHandler implements Handler {

    private final FileService fileService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.START;
    }

    @Override
    public Object handle(Message message) {

        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(List.of(List.of(
                        InlineKeyboardButton.builder()
                                .text("Я все понял, можно продолжать \uD83D\uDD25")
                                .callbackData(StateBot.MAIN.name())
                                .build())))
                .build();
        var video = fileService.findById(1L);
        return SendVideo.builder()
                .chatId(message.getChatId())
                .video(new InputFile(video.getFileId()))
                .replyMarkup(replyKeyboard)
                .caption(getCurrentState().getMessage())
                .build();
    }
}
