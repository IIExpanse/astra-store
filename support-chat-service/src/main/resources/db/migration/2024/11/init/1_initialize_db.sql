CREATE SCHEMA IF NOT EXISTS users;

CREATE SCHEMA IF NOT EXISTS chat;

CREATE SEQUENCE IF NOT EXISTS chat.chat_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS chat.message_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS users.user_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS users.user
(
    id    BIGINT,
    email VARCHAR(20) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS chat.chat
(
    id   BIGINT,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_chat PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS chat.chat_user
(
    chat_id   BIGINT,
    user_id   BIGINT,
    user_role VARCHAR(20) NOT NULL,
    CONSTRAINT pk_chat_user PRIMARY KEY (chat_id, user_id)
);

CREATE TABLE IF NOT EXISTS chat.message
(
    id         BIGINT,
    text       VARCHAR(1000) NOT NULL,
    timestamp  timestamptz   NOT NULL,
    replied_to BIGINT,
    author_id  BIGINT        NOT NULL,
    chat_id    BIGINT        NOT NULL,
    CONSTRAINT pk_message PRIMARY KEY (id),
    CONSTRAINT not_replied_to_itself CHECK (id <> message.replied_to)
);

ALTER TABLE chat.message
    ADD CONSTRAINT fk_replied_to FOREIGN KEY (replied_to) REFERENCES chat.message (id);

ALTER TABLE chat.message
    ADD CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES users.user (id);

ALTER TABLE chat.message
    ADD CONSTRAINT fk_chat_id FOREIGN KEY (chat_id) REFERENCES chat.chat (id);

ALTER TABLE chat.chat_user
    ADD CONSTRAINT fk_chat_id FOREIGN KEY (chat_id) REFERENCES chat.chat (id);

ALTER TABLE chat.chat_user
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users.user (id);