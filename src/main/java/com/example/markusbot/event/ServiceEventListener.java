package com.example.markusbot.event;

import com.example.markusbot.bot.TelegramBot;
import com.example.markusbot.enumBot.StateBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.example.markusbot.log.Constant.ERROR_WITH_SENDING_MESSAGE;
import static com.example.markusbot.service.FileService.CALCULATION_FIRST_ID;
import static com.example.markusbot.service.FileService.CALCULATION_SECOND_ID;


@Component
@Slf4j
@RequiredArgsConstructor
public class ServiceEventListener {

    private final TelegramBot telegramBot;

    @Async
    @EventListener
    public void handleSendTelegramMessageEvent(SendTelegramMessageEvent event) {
        try {
            telegramBot.execute(
                    SendMessage.builder()
                            .replyToMessageId(event.getReplyMessageId())
                            .text(event.getText())
                            .chatId(event.getChatId())
                            .build()
            );
        } catch (TelegramApiException e) {
            log.debug(ERROR_WITH_SENDING_MESSAGE, e);
        }
    }

    @Async
    @EventListener
    public void handleSendPhotoEvent(SendPhotoEvent event) {

        List<InputMedia> inputMediaPhotos = event.getPhotos().stream()
                .map(InputMediaPhoto::new)
                .map(photo -> (InputMedia) photo)
                .toList();

        inputMediaPhotos.get(inputMediaPhotos.size() - 1).setCaption(event.getText());

        try {
            telegramBot.execute(
                    SendMediaGroup.builder()
                            .chatId(event.getChatId())
                            .medias(inputMediaPhotos)
                            .build()
            );
        } catch (TelegramApiException e) {
            log.debug(ERROR_WITH_SENDING_MESSAGE, e);
        }
    }
}
