package com.example.markusbot.handler.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.FileService;
import com.example.markusbot.service.OrderService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import java.util.List;

import static com.example.markusbot.service.FileService.CALCULATION_FIRST_ID;
import static com.example.markusbot.service.FileService.CALCULATION_SECOND_ID;

@Component
@RequiredArgsConstructor
public class OrderPriceHandler implements Handler {

    private final OrderService orderService;

    private final UserService userService;

    private final FileService fileService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_PRICE;
    }

    @Override
    public Object handle(Message message) {
        if (!NumberUtils.isCreatable(message.getText()) || Double.parseDouble(message.getText()) <= 0) {
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text("Неверный ввод, введите положительное число для того чтобы указань размер товара в сантиметрах")
                    .build();
        }
        orderService.setSmSize(Double.parseDouble(message.getText()), message);

        var file1 = fileService.findById(CALCULATION_FIRST_ID);
        var file2 = fileService.findById(CALCULATION_SECOND_ID);

        var media1 = new InputMediaPhoto(file1.getFileId());
        var media2 = new InputMediaPhoto(file2.getFileId());
        media2.setCaption(getCurrentState().getMessage());

        userService.updateUserState(message.getChatId(), StateBot.ORDER_PRICE_SAVE);

        return SendMediaGroup.builder()
                .chatId(message.getChatId())
                .medias(List.of(media2, media1))
                .build();
    }
}
