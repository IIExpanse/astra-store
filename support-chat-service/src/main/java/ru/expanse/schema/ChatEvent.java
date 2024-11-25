package ru.expanse.schema;

public record ChatEvent(Long chatId, ChatAction action) {
}
