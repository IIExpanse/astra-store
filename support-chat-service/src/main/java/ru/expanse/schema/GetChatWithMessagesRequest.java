package ru.expanse.schema;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record GetChatWithMessagesRequest(
        @NotNull
        Long chatId,
        @NotNull
        OffsetDateTime from,
        @NotNull
        OffsetDateTime to
) {
}
