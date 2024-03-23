package com.example.markusbot.handler.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.OrderService;
import com.example.markusbot.service.SettingService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class OrderPriceSaveHandler implements Handler {

    private final OrderService orderService;
    private final SettingService settingService;
    private final UserService userService;

    private final OrderApproveHandler orderApproveHandler;


    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_PRICE_SAVE;
    }

    @Override
    public Object handle(Message message) {
        if (!NumberUtils.isCreatable(message.getText()) || Double.parseDouble(message.getText()) <= 0) {
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text("Неверный ввод, введите положительное число для того чтобы расчитать стоимость!")
                    .build();
        }
        var setting = settingService.getSetting();
        var yan = Double.parseDouble(message.getText());
        var result = yan * setting.getYuanExchangeRate() + setting.getCommission();

        orderService.updatePrice(message, yan, result);
        userService.updateUserState(message.getChatId(), StateBot.ORDER_APPROVE);

        return orderApproveHandler.handle(message);
    }
}
