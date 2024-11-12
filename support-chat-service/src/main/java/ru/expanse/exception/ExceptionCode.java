package ru.expanse.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    CHAT_NOT_FOUND("chat not found"),
    MESSAGE_NOT_FOUND("message not found"),
    USER_NOT_FOUND("user not found"),
    CHAT_USER_ASSOCIATION_NOT_FOUND("chat user association not found"),;

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }
}
