CREATE SCHEMA IF NOT EXISTS "notifications";

create table "notifications"."tokens"
(
    id    UUID primary key,
    user_id UUID not null,
    token varchar(255) unique not null
);

CREATE TABLE "notifications"."notifications"
(
    id         UUID PRIMARY KEY,
    user_id    UUID         not null,
    type       VARCHAR(255) NOT NULL,
    title      VARCHAR(255) NOT NULL,
    message    TEXT         NOT NULL,
    created_at TIMESTAMP DEFAULT nOW(),
    sent_at TIMESTAMP
);