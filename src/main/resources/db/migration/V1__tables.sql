CREATE SCHEMA IF NOT EXISTS "notifications";

create table "notifications"."tokens"
(
    id    UUID primary key,
    token varchar(255) unique not null,
    constraint fk_tokens_on_user foreign key (id) references "user"."users" (id) on delete cascade
);

create table "notifications"."images"
(
    id  UUID primary key,
    uri varchar(1000)
);

CREATE TABLE "notifications"."notifications"
(
    id         UUID PRIMARY KEY,
    user_id    UUID         not null,
    image      UUID references "notifications"."images" (id),
    type       VARCHAR(255) NOT NULL,
    title      VARCHAR(255) NOT NULL,
    message    TEXT         NOT NULL,
    created_at TIMESTAMP DEFAULT nOW(),
    sent_at TIMESTAMP,

    constraint fk_notifications_on_user foreign key (user_id) references "user"."users" (id) on delete cascade
);