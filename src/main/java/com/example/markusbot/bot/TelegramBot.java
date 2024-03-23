package com.example.markusbot.bot;

import com.example.markusbot.service.UpdateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

import static com.example.markusbot.log.Constant.ERROR_WITH_SENDING_MESSAGE;


@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final UpdateHandler updateHandler;
    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String botName;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (Objects.nonNull(update.getMyChatMember()) || messageFromGroup(update)) {
            return;
        }

        Object message = updateHandler.handleMessage(update);

        if (Objects.isNull(message)) {
            return;
        }

        try {
            if (message instanceof EditMessageText editMessageText) {
                execute(editMessageText);
            } else if (message instanceof SendMessage sendMessage) {
                execute(sendMessage);
            } else if (message instanceof SendPhoto sendPhoto) {
                execute(sendPhoto);
            } else if (message instanceof SendDocument sendDocument) {
                execute(sendDocument);
            } else if (message instanceof SendAudio sendAudio) {
                execute(sendAudio);
            } else if (message instanceof SendVideo sendVideo) {
                execute(sendVideo);
            } else if (message instanceof SendMediaGroup sendMediaGroup) {
                execute(sendMediaGroup);
            }
        } catch (TelegramApiException e) {
            log.info(ERROR_WITH_SENDING_MESSAGE, e);
        }
    }

    private boolean messageFromGroup(Update update) {
        // разрешенные действия будем пропускать
        boolean result = true;
        if (Objects.nonNull(update.getMessage()) && update.getMessage().isUserMessage()) {
            // любое сообщение в личке (текст, фото, документ)
            result = false;
        }
        if (Objects.nonNull(update.getCallbackQuery()) && update.getCallbackQuery().getMessage().isUserMessage()) {
            // любая кнопка в личке
            result = false;
        }
        return result;
    }
}
