package com.example.markusbot.handler;

import com.example.markusbot.enumBot.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class FeedBackHandler implements Handler {
    @Override
    public StateBot getCurrentState() {
        return StateBot.FEEDBACK;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardDefault(StateBot.MAIN);
        return getDefaultMessage(message, keyboard);
    }
}
