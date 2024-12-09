package ru.expanse.schema;

import java.util.List;

public record GetChatWithMessagesResponse(
        Long id,
        String name,
        List<MessageRecord> messages
) {
}
