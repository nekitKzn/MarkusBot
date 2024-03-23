package com.example.markusbot.enumBot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum StateBot {


    /**
     * USER STATE
     */
    START("""
            Привет, это Golden 21! 👋🏻 Помогу быстро рассчитать и оформить заказ с Poizon, где представлены оригиналы брендов
            
            но для начала посмотрите короткое видео о том что такое Poizon и как делать заказы))
            """,
            null),

    MAIN("", "Вернуться в меню ↩️"),

    CREATE_QUESTION("""
            Напишите ваш вопрос менеджеру одним сообщением:
            """, "Задать вопрос менеджеру ✍️"),
    CREATE_QUESTION_SAVE("""
            ✅ Ваш вопрос принят в обработу!
            
            Скоро первый освободившийся менеджер вам ответит ✔️
            """, "Задать вопрос менеджеру ✍️"),

    CREATE_ORDER("""
            Отличное решение! 🛍
            
            Давайте создаим заказ 🙌
            Пришлите из приложения скрин с товаром, чтобы мы поняли что вы хотите оформить))
            """, "Оформить заказ ✅"),

    ORDER_SM("""
            Прекрасно! 📖
            
            Пришлите размер товара в сантиметрах:
            """, null),

    ORDER_EUROPE("""
            Отлично! 🙂
            
            Теперь пришлите размер по Европейской шкале:
            """, null),

    ORDER_PRICE("""
            Напиши сюда цифру с бирюзовой кнопки из приложения POIZON\uD83D\uDC47\uD83C\uDFFB
            """, null),

    ORDER_PRICE_SAVE("", null),

    ORDER_APPROVE("""
            Ваш заказ 🙌
            
            Номер заказа: %s
            
            Цена в рублях: %s
            Цена в юанях: %s
            """, null),

    ORDER_DELETED("", null),

    ORDER_FIO("""
            Продолжаем оформлять заказ!
            
            Для оформления заказа потребуется ваше ФИО:
            """, null),

    ORDER_ADDRESS("""
            Далее, чтобы оформить заказ нам нужно знать куда его отправить, поэтому пришлите полный адресс сдека с названием города:
            """, null),

    ORDER_PHONE_NUMBER("""
            И наконец, напишите сюда ваш номер телефона, чтобы менеджер смог связаться с вами в случае каких либо вопросов:
            """, null),

    ORDER_PHONE_NUMBER_SAVE("", null),

    ORDER_PAY("""
            Прекрасно, мы на финишной прямой 👏
            Номер вашего заказа: %s
            
            Сумма заказа: %s
            Размер по Европейской шкале: %s
            ФИО: %s
            Адрес доставки: %s
            Номер телефона: %s
            
            Осталось только оплатить товар, отправив средвтва переводом.
            Мы работаем только по 100 процентной предоплате, поэтмому без подтверждрения платежа не сможем заказать товар.
            
            Реквизиты для перевода:
            +7 (987) 286 52 65 Анета
            
            После того как отпрвите средства, ждем от вас здесь скрин о переводе:
            """, null),

    ORDER_PAY_SUCCESS("""
            Поздравляю, вы оформили заказ в нашем боте. Ожидайте обратную связь от менеджера.
            """, null),

    CALCULATION("Напиши сюда цифру с бирюзовой кнопки из приложения POIZON\uD83D\uDC47\uD83C\uDFFB", "Рассчитать стоимсоть \uD83D\uDC5F\uD83D\uDC54"),
    CALCULATION_RESULT("""
            Итоговая стоимость товара с доставкой до Москвы: _%s_ 💵
                        
            _Доставка из Москвы до вашего адреса рассчитывается индивидуально менеджером при заказе! Примерная стоимость доставки составляет 350₽_
            """, "Рассчитать стоимсоть \uD83D\uDC5F\uD83D\uDC54"),

    FEEDBACK("""
            Отзывы наших клиентов:
            
            тут надо будет тоже что-то придуматЬ, может сслыки на какие то ресурсы, либо еще что
            
           """, "Отзывы"),


    ABOUT("""
            Это лушчий бот для заказа брендовой одежды из приложения POIZON
            
            Тут тебе надо сделать описание, может рассказать что-то оп поставках, о скорости работы, о количестве сделанных заказов, ну кароче чтобы пользватель мог тут познакомиться с тобой 
            """, "About Golden 21"),






    /**
     * MANAGER STATE
     */
    MANAGER_MAIN("""
            Панель менеджера для управления ботом:
            
            Выбранный курс юаня: %s
            Выбранная комиссия на один заказ: %s
            
            Итоговая сумма для заказа вычисляется так:
            (курс юаня) ✖️ (стоимость покупки) ➕ комиссия
            """, "⬅️"),
    QUESTION_LIST("""
            Здесь хранятся неотвеченные вопросы от пользователей.
            
            %s
            """, "❓ Неотвеченные вопросы ❓"),
    QUESTION_SELECTED("""
            Вопрос звучал так:
                        
            «%s»
                        
            Напишите здесь ответ. Он сразу уйдет отправителю.
            """, null),
    ANSWER_SEND("Отлично! Ответ отправлен автору вопроса!", null),
    ORDER_LIST("Вот список всех заказов:","\uD83D\uDECD Список закзов \uD83D\uDECD"),
    ORDER_SELECTED("", null),
    CHANGE_COMMISSION("Введите новую сумму для комиссии:  ", "Изменть комиссию \uD83D\uDCB0"),
    SAVE_COMMISSION("Новая комиссия успешно сохранена!", null),
    CHANGE_YUAN("Введите курс юаня по которому будут расчитываться стоимость товаров: ", "Изменить курс ЮАНЯ \uD83D\uDCB8"),
    SAVE_YUAN("Новый курс успешно сохранен!", null),
    CHANGE_MENU("Пришлите фото для меню: ", "Изменить фото на меню \uD83D\uDDBC"),
    SAVE_MENU("Новое фото для меню установлено!", null),





    /**
     * ADMIN STATE
     */
    ADMIN_MAIN("Панель администратора!", "⬅️"),


    ADMIN_LIST_USERS_ALL_COUNT("""
            Список пользователей общим количеством дейстивий:
                    
            %s
            """, "Пользователи ALl"),

    ADMIN_LIST_USERS_UPDATE_COUNT("""
            Список пользователей со сбрасываемым количеством действий:
                    
            %s
            """, "Пользователи Time");

    private final String message;
    private final String buttonInThisState;

    public static StateBot getStateBotByCallBackQuery(String query) {
        return Arrays.stream(values())
                .filter(state -> query.equals(state.name()))
                .findFirst()
                .orElse(null);
    }
}
