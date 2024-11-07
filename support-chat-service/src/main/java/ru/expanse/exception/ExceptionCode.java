package ru.expanse.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    MESSAGE_NOT_FOUND("message not found"),
    USER_NOT_FOUND("user not found");

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }
}
