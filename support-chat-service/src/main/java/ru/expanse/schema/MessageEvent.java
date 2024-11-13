package ru.expanse.schema;

public record MessageEvent(
        Long messageId,
        Long chatId,
        MessageAction action
) {
}
