package com.example.markusbot.service;

import com.example.markusbot.event.SendPhotoEvent;
import com.example.markusbot.event.SendTelegramMessageEvent;
import com.example.markusbot.model.OrderEntity;
import com.example.markusbot.model.QuestionEntity;
import com.example.markusbot.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
public class LetterSender {

    private final ApplicationEventPublisher publisher;

    private final static String NEW_MANAGER_MESSAGE = """
            Приветствуем Вас, %s, Вас сделали менеджером в боте 'GOLDEN 21' .\s
            Теперь у вас доступна админка менеджера через команду /manager\s
                        
            Через нее вы сможете отвечать на вопросы пользователей, обрабатывать заказы и управлять ботом\s
            Благословений! С Богом! 🙏""";

    private final static String NEW_ADMIN_MESSAGE = """
            Приветствуем Вас, %s, Вас сделали администратором в боте 'GOLDEN 21' .\s
            Теперь у вас доступна админка через команду /admin\s
                        
            Через нее вам доступен весь функционал системы""";

    private final static String NEW_QUESTION = """
            Поступил вопрос от пользователя %s
            Содержание:
                        
            «%s»
            
            Чтобы ответить на него, необходимо зайти в панель управления менеджера введя команду: /manager
            """;

    private final static String ANSWER = """
            Поступил ответ от менеджера GOLDEN 21
                        
            Ваш вопрос звучал так:
            «%s»
                        
            Ответ:
            «%s»
            """;

    private final static String ORDER = """
            Новый заказ GOLDEN 21!
            
            Номер заказа: GD%s
                        
            Имя в телеграмме: %s
            Ссылка на аккаунт: %s
            Фио: %s
            Номер телефона: %s
            
            Цена в юанях: %s
            Цена в рублях: %s
            Адрес доставки: %s
            Размер по Европейской шкале: %s
            Размер в сантиметрах: %s
            
            Все заказы можно посмотреть через /manager
            """;

    private final static String LOG_TEMPLATE_ANSWER = "{} отвечает {}: ({}) {}";

    public void letterNewOrder(OrderEntity order, List<UserEntity> managers) {
        var author = order.getAuthor();
        managers.forEach(manager -> publishWithPhoto(manager.getTelegramId(),
                List.of(order.getPhoto().getFileId(), order.getCheck().getFileId()),
                ORDER,
                order.getId(),
                author.getTelegramFirstName(),
                Objects.isNull(author.getTelegramUserName()) ? "нету" : "@" + author.getTelegramUserName(),
                order.getFio(),
                order.getPhoneNumber(),
                order.getPricePoizon(),
                order.getPriceRub(),
                order.getAddress(),
                order.getSizeEurope(),
                order.getSizeSm()
        ));
    }

    public void letterNewAdmin(UserEntity user) {
        publishText(user.getTelegramId(), null, NEW_ADMIN_MESSAGE, user.getTelegramFirstName());
    }

    public void letterNewManager(UserEntity user) {
        publishText(user.getTelegramId(), null, NEW_MANAGER_MESSAGE, user.getTelegramFirstName());
    }

    public void letterNewQuestion(QuestionEntity question, List<UserEntity> managers) {
        log.info(question.getAuthor().getTelegramFirstName() + " задает вопрос: " + question.getText());
        UserEntity user = question.getAuthor();
        String name = user.getTelegramFirstName() +  " " + (Objects.nonNull(user.getTelegramUserName()) ? "@" + user.getTelegramUserName() : "");
        managers.forEach(manager -> publishText(manager.getTelegramId(), null, NEW_QUESTION, name, question.getText()));
    }

    public void letterNewAnswer(QuestionEntity entity) {
        log.info(LOG_TEMPLATE_ANSWER, entity.getManager().getTelegramFirstName(), entity.getAuthor().getTelegramFirstName(), entity.getText(), entity.getAnswer());
        publishText(entity.getAuthor().getTelegramId(), null, ANSWER, entity.getText(), entity.getAnswer());
    }

    private void publishText(Long to, Integer replyMessageId, String template, Object... args) {
        String text = isEmpty(args) ? template : String.format(template, args);
        publisher.publishEvent(new SendTelegramMessageEvent(this, text, to, replyMessageId));
    }

    private void publishWithPhoto(Long to, List<String> photos, String template, Object... args) {
        String text = isEmpty(args) ? template : String.format(template, args);
        publisher.publishEvent(new SendPhotoEvent(this, to, photos, text));
    }


}
