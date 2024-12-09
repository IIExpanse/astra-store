package ru.expanse.schema;

import java.time.OffsetDateTime;

public record GetChatWithMessagesRequest(
        Long chatId,
        OffsetDateTime from,
        OffsetDateTime to
) {
}
