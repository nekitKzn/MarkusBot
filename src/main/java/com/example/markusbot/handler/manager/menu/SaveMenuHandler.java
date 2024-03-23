package com.example.markusbot.handler.manager.menu;

import com.example.markusbot.enumBot.StateBot;
import com.example.markusbot.handler.Handler;
import com.example.markusbot.service.FileService;
import com.example.markusbot.service.SettingService;
import com.example.markusbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class SaveMenuHandler implements Handler {

    private final SettingService settingService;
    private final UserService userService;
    private final FileService fileService;


    @Override
    public StateBot getCurrentState() {
        return StateBot.SAVE_MENU;
    }

    @Override
    public Object handle(Message message) {
        var file = fileService.createNewPhoto(message);
        settingService.updateMenu(file);
        var keyboard = getKeyboardDefault(StateBot.MANAGER_MAIN);
        userService.updateUserState(message.getChatId(), StateBot.MANAGER_MAIN);
        return getDefaultMessage(message, keyboard);
    }
}
