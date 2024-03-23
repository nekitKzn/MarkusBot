package com.example.markusbot.handler.manager.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.model.OrderEntity;
import com.example.markusbot.model.QuestionEntity;
import com.example.markusbot.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import java.util.List;

import static com.example.markusbot.service.FileService.CALCULATION_FIRST_ID;
import static com.example.markusbot.service.FileService.CALCULATION_SECOND_ID;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderSelectedHandler implements Handler {

    private final OrderService orderService;

    private final static String ORDER = """
            Номер заказа: %s
                        
            Имя в телеграмме: %s
            Ссылка на аккаунт: %s
            Фио: %s
            Номер телефона: %s
            
            Цена в юанях: %s
            Цена в рублях: %s
            Адрес доставки: %s
            Размер по Европейской шкале: %s
            Размер в сантиметрах: %s
            """;


    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_SELECTED;
    }

    @Override
    public Object handle(Message message) {
        var order = orderService.findById(Long.valueOf(message.getText()));
        logSelectedButton(message, order);

        var text = String.format(ORDER,
                order.getOrderNumber(),
                order.getAuthor().getTelegramFirstName(),
                order.getAuthor().getLinkUser(),
                order.getFio(),
                order.getPhoneNumber(),
                order.getPricePoizon(),
                order.getPriceRub(),
                order.getAddress(),
                order.getSizeEurope(),
                order.getSizeSm()
        );

        var media1 = new InputMediaPhoto(order.getPhoto().getFileId());
        var media2 = new InputMediaPhoto(order.getCheck().getFileId());
        media2.setCaption(text);

        return SendMediaGroup.builder()
                .chatId(message.getChatId())
                .medias(List.of(media2, media1))
                .build();
    }

    private static void logSelectedButton(Message message, OrderEntity order) {
        log.info("User @{}:{} select button: {}", message.getChat().getUserName(),
                message.getChat().getFirstName(), order.getOrderNumber());
    }
}
