package com.example.markusbot.handler.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.OrderService;
import com.example.markusbot.service.SettingService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.example.markusbot.util.TelegramUtil.createButtonByState;

@Component
@RequiredArgsConstructor
public class OrderApproveHandler implements Handler {

    private final OrderService orderService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_APPROVE;
    }

    @Override
    public Object handle(Message message) {

        var order = orderService.getOrder(message);


        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        List.of(InlineKeyboardButton.builder()
                                .text("Продолжаем оформлять заказ! ")
                                .callbackData("ORDER_FIO")
                                .build()
                        ),
                        List.of(InlineKeyboardButton.builder()
                                .text("Я передумал, давайте вернемся в главное меню")
                                .callbackData("ORDER_DELETED")
                                .build()
                        )
                ))
                .build();

        return SendPhoto.builder()
                .chatId(message.getChatId())
                .photo(new InputFile(order.getPhoto().getFileId()))
                .caption(String.format(getCurrentState().getMessage(),
                        order.getOrderNumber(),
                        order.getPriceRub(),
                        order.getPricePoizon() ))
                .replyMarkup(replyKeyboard)
                .build();
    }
}
