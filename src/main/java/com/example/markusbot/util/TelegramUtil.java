package com.example.markusbot.util;


import com.example.markusbot.enumBot.FileType;
import com.example.markusbot.enumBot.FunctionBot;
import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.model.FileEntity;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Triple;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Objects;

import static com.example.markusbot.enumBot.FileType.*;
import static com.example.markusbot.enumBot.FileType.AUDIO;

@UtilityClass
public class TelegramUtil {


    public static InlineKeyboardButton createButtonByState(StateBot state) {
        return InlineKeyboardButton.builder()
                .text(state.getButtonInThisState())
                .callbackData(state.name())
                .build();
    }

    public static InlineKeyboardButton createButtonByFunction(FunctionBot functionBot) {
        return InlineKeyboardButton.builder()
                .text(functionBot.getButtonForThisFunction())
                .callbackData(functionBot.name())
                .build();
    }

    public static InlineKeyboardButton button(String text, Long callback) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(Long.toString(callback))
                .build();
    }

    public static InlineKeyboardButton createButtonWithUrl(String text, String command, String url) {
        return InlineKeyboardButton.builder()
                .text(text)
                .url(url)
                .callbackData(command)
                .build();
    }


    public static String getNumberSpase(Long number) {
        if (number == 0) {
            return " ".repeat(8);
        } else {
            return " ".repeat(10 - ((int) Math.log10(number) + 1) * 2);
        }
    }

    public static Object sendDocVidAudPhoto(Long chatId, FileEntity entity, InlineKeyboardMarkup keyboard, StateBot stateBot) {
        return switch (entity.getFileType()) {
            case PHOTO -> SendPhoto.builder()
                    .chatId(chatId)
                    .photo(new InputFile(entity.getFileId()))
                    .replyMarkup(keyboard)
                    .caption(String.format(stateBot.getMessage(), entity.getFileName()))
                    .build();
            case VIDEO -> SendVideo.builder()
                    .chatId(chatId)
                    .video(new InputFile(entity.getFileId()))
                    .replyMarkup(keyboard)
                    .caption(String.format(stateBot.getMessage(), entity.getFileName()))
                    .build();
            case DOCUMENT -> SendDocument.builder()
                    .chatId(chatId)
                    .caption(String.format(stateBot.getMessage(), entity.getFileName()))
                    .replyMarkup(keyboard)
                    .document(new InputFile(entity.getFileId()))
                    .build();
            case AUDIO -> SendAudio.builder()
                    .chatId(chatId)
                    .replyMarkup(keyboard)
                    .caption(String.format(stateBot.getMessage(), entity.getFileName()))
                    .audio(new InputFile(entity.getFileId()))
                    .build();
        };
    }

    private static Triple<FileType, String, String> defineFileType(Message message) {
        if (message.hasPhoto()) {
            var photo = message.getPhoto().get(message.getPhoto().size() - 1);
            return Triple.of(PHOTO, photo.getFileId(), "фото");
        }
        if (message.hasDocument()) {
            return Triple.of(DOCUMENT, message.getDocument().getFileId(), message.getDocument().getFileName());
        }
        if (message.hasVideo()) {
            return Triple.of(VIDEO, message.getVideo().getFileId(), message.getVideo().getFileName());
        }
        if (message.hasAudio()) {
            return Triple.of(AUDIO, message.getAudio().getFileId(), message.getAudio().getFileName());
        }
        return null;
    }


}
