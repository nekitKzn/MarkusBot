package com.example.markusbot.service;

import com.example.markusbot.model.QuestionEntity;
import com.example.markusbot.model.UserEntity;
import com.example.markusbot.repository.QuestionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository repository;

    private final UserService userService;
    private final LetterSender letterSender;

    @Transactional
    public void saveNewQuestion(Message message) {
        QuestionEntity question = QuestionEntity.builder()
                .author(userService.findByTelegramId(message.getFrom().getId()))
                .text(message.getText())
                .build();
        question = repository.save(question);
        letterSender.letterNewQuestion(question, userService.getManagers());
    }


//    @Transactional
//    public void addTextInLastQuestion(UserEntity user, String text) {
//        QuestionEntity entity = repository.findFirstByAuthorOrderByCreatedAtDesc(user).orElseThrow();
//        entity.setText(text);
//        entity = repository.save(entity);
//        letterSender.letterNewQuestionPersonally(entity);
//    }

    @Transactional
    public QuestionEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void selectQuestion(QuestionEntity question, Long id) {
        var manager = userService.findByTelegramId(id);
        question.setSelectNumber(question.getSelectNumber() + 1);
        question.setManager(manager);
        repository.save(question);
    }

    @Transactional
    public Map<Long, String> getQuestionMap() {
        return repository.findAllByAnswerIsNull().stream()
                .collect(Collectors.toMap(QuestionEntity::getId, QuestionEntity::getText));
    }

    @Transactional
    public void saveAnswerSelectedQuestion(UserEntity manager, String answer) {
        QuestionEntity entity = repository.findFirstByManagerOrderByUpdatedAtDesc(manager).orElseThrow();
        entity.setAnswer(answer);
        repository.save(entity);
        letterSender.letterNewAnswer(entity);
    }
}
