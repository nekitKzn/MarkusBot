package com.example.markusbot.event;

import org.springframework.context.ApplicationEvent;

import java.util.List;

public class SendPhotoEvent extends ApplicationEvent {

    private final String text;
    private final List<String> photos;

    private final Long chatId;

    public SendPhotoEvent(Object source, Long chatId, List<String> photos, String text) {
        super(source);
        this.text = text;
        this.photos = photos;
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public Long getChatId() {
        return chatId;
    }
}
