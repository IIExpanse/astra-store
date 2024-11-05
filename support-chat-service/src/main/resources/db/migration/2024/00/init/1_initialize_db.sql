CREATE SCHEMA IF NOT EXISTS users;

CREATE SCHEMA IF NOT EXISTS chat;

CREATE SEQUENCE IF NOT EXISTS chat.message_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS users.users
(
    id    BIGINT,
    email VARCHAR(20),
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS chat.message
(
    id         BIGINT,
    text       VARCHAR(1000) NOT NULL,
    timestamp  timestamptz,
    replied_to BIGINT,
    author_id  BIGINT,
    CONSTRAINT pk_message PRIMARY KEY (id),
    CONSTRAINT not_replied_to_itself CHECK (id <> message.replied_to)
);

ALTER TABLE chat.message
    ADD CONSTRAINT fk_replied_to FOREIGN KEY (replied_to) REFERENCES chat.message (id);

ALTER TABLE chat.message
    ADD CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES users.users (id);