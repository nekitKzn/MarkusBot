create table "users"
(
    telegram_id            numeric     not null primary key,
    created_at             timestamp   not null default now(),
    updated_at             timestamp   not null default now(),
    telegram_user_name     varchar(50),
    telegram_first_name    varchar(50),
    state                  varchar(50) not null,
    admin                  boolean     not null default false,
    manager                boolean     not null default false,
    block                  boolean     not null default false,
    count_change_state     numeric     not null default 0,
    count_change_state_all numeric     not null default 0
);

create table questions
(
    id            numeric   not null primary key,
    created_at    timestamp not null default now(),
    updated_at    timestamp not null default now(),
    author_id     numeric   not null references "users" (telegram_id),
    manager_id    numeric references "users" (telegram_id),
    text          varchar,
    answer        varchar,
    select_number numeric   not null default 0
);

create table files
(
    id         numeric   not null primary key,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    file_name  varchar,
    user_id    numeric   not null references "users" (telegram_id),
    file_id    varchar,
    file_type  varchar(20)
);

create table orders
(
    id           numeric   not null primary key,
    created_at   timestamp not null default now(),
    updated_at   timestamp not null default now(),
    status       varchar   not null,
    price_rub    numeric,
    price_poizon numeric,
    user_id      numeric   not null references "users" (telegram_id),
    photo_id     numeric references "files" (id),
    link         varchar,
    size_sm      numeric,
    size_europe  numeric,
    fio          varchar,
    address      varchar,
    phone_number varchar,
    check_id     numeric references "files" (id)
);

create table settings
(
    id                 numeric   not null primary key,
    created_at         timestamp not null default now(),
    updated_at         timestamp not null default now(),
    file_id_menu       numeric references "files" (id),
    yuan_exchange_rate numeric   not null default 13,
    commission         numeric   not null default 350
);

create index idx_users_username on users (telegram_user_name);

INSERT INTO public.users (telegram_id, telegram_user_name, telegram_first_name, state, admin, manager)
VALUES (761245887, 'nekit_vp', 'Nekit', 'START', true, true);
INSERT INTO public.files (id, file_name, user_id, file_id, file_type)
VALUES (1, 'text', 761245887, 'BAACAgIAAxkBAAMSZfAkYfob-bjvkJmcQHExTS40IJgAAhtGAALO34BLthTx97OGl-k0BA', 'VIDEO');
INSERT INTO public.files (id, file_name, user_id, file_id, file_type)
VALUES (2, 'sdf', 761245887, 'AgACAgIAAxkBAAOfZfA_IuS-yRMs7qEy9QABGCekdOx1AAL22DEbzt-AS06UFkjAXe9TAQADAgADbQADNAQ',
        'PHOTO');
INSERT INTO public.settings (id, file_id_menu, yuan_exchange_rate, commission)
VALUES (1, 2, 13, 350);
INSERT INTO public.files (id, file_name, user_id, file_id, file_type)
VALUES (3, 'photo', 761245887, 'AgACAgIAAxkBAAP2ZfB6QsQr7k2ZQRnSzHuKfGG2URoAAsbZMRvO34hLuCmpspg0ZyEBAAMCAAN5AAM0BA',
        'PHOTO');

INSERT INTO public.files (id, file_name, user_id, file_id, file_type)
VALUES (4, 'photo', 761245887, 'AgACAgIAAxkBAAP3ZfB6QlmAH-nJKya7XFKOycPD9JMAAsfZMRvO34hLhgtuJCXZ6JwBAAMCAAN5AAM0BA',
        'PHOTO');


