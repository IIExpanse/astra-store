package ru.expanse.schema;

public record ChatEvent(Long chatId, Long recipientId, ChatAction action) {
}
