package com.example.markusbot.handler;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.service.FileService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import java.util.List;

import static com.example.markusbot.service.FileService.CALCULATION_FIRST_ID;
import static com.example.markusbot.service.FileService.CALCULATION_SECOND_ID;

@Component
@RequiredArgsConstructor
public class CalculationHandler implements Handler {

    private final FileService fileService;

    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CALCULATION;
    }

    @Override
    public Object handle(Message message) {

        var file1 = fileService.findById(CALCULATION_FIRST_ID);
        var file2 = fileService.findById(CALCULATION_SECOND_ID);

        var media1 = new InputMediaPhoto(file1.getFileId());
        var media2 = new InputMediaPhoto(file2.getFileId());
        media2.setCaption(getCurrentState().getMessage());

        userService.updateUserState(message.getChatId(), StateBot.CALCULATION_RESULT);

        return SendMediaGroup.builder()
                .chatId(message.getChatId())
                .medias(List.of(media2, media1))
                .build();
    }
}
