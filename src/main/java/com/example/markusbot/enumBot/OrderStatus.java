package com.example.markusbot.enumBot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    NEW,
    CREATED,
    READY,
    DELETED;
}
