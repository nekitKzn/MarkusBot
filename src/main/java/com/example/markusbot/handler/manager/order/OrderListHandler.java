package com.example.markusbot.handler.manager.order;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.markusbot.util.TelegramUtil.button;
import static com.example.markusbot.util.TelegramUtil.createButtonByState;

@Component
@RequiredArgsConstructor
public class OrderListHandler implements Handler {

    private final OrderService orderService;

    private final static String NOT_FOUND_ORDER = "Заказов к сожалению нет \uD83D\uDE09";
    private final static String SUCCESS_FIND_ORDER = "Выберите заказ чтобы посмотреть детали: ";



    @Override
    public StateBot getCurrentState() {
        return StateBot.ORDER_LIST;
    }

    @Override
    public StateBot getNextState() {
        return StateBot.ORDER_SELECTED;
    }

    @Override
    public Object handle(Message message) {
        Map<Long, String> listOrders = orderService.getOrderMap();

        List<List<InlineKeyboardButton>> lists = new ArrayList<>(listOrders.entrySet().stream()
                .map(entry -> button(entry.getValue(), entry.getKey()))
                .map(List::of).toList());
        lists.add(List.of(createButtonByState(StateBot.MANAGER_MAIN)));
        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(lists).build();

        if (listOrders.isEmpty()) {
            return getDefaultMessage(message, replyKeyboard, NOT_FOUND_ORDER);
        } else {
            return getDefaultMessage(message, replyKeyboard, SUCCESS_FIND_ORDER);
        }
    }
}
