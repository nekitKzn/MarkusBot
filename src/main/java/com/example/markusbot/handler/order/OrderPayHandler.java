package com.example.markusbot.handler.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.OrderService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class OrderPayHandler implements Handler {

    private final OrderService orderService;
    private final UserService userService;



    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_PAY;
    }

    @Override
    public StateBot getNextState() {
        return StateBot.ORDER_PAY_SUCCESS;
    }

    @Override
    public Object handle(Message message) {
        var order = orderService.getOrder(message);
        return SendPhoto.builder()
                .chatId(message.getChatId())
                .photo(new InputFile(order.getPhoto().getFileId()))
                .caption(String.format(getCurrentState().getMessage(),
                        order.getOrderNumber(),
                        order.getPriceRub(),
                        order.getSizeEurope(),
                        order.getFio(),
                        order.getAddress(),
                        order.getPhoneNumber()))
                .replyMarkup(null)
                .build();
    }
}
