package com.example.markusbot.service;

import com.example.markusbot.enumBot.FileType;
import com.example.markusbot.model.FileEntity;
import com.example.markusbot.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;


@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    public final static Long CALCULATION_FIRST_ID = 3L;
    public final static Long CALCULATION_SECOND_ID= 4L;

    private final FileRepository repository;
    private final UserService userService;

    @Transactional
    public FileEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public FileEntity createNewPhoto(Message message) {
        PhotoSize photoSize = message.getPhoto().get(message.getPhoto().size() - 1);
        var photo = FileEntity.builder()
                .fileId(photoSize.getFileId())
                .fileName("photo")
                .fileType(FileType.PHOTO)
                .author(userService.findByTelegramId(message.getFrom().getId()))
                .build();
        return repository.save(photo);
    }
}
