package ru.expanse.schema;

import jakarta.validation.constraints.NotNull;

public record SaveChatRequest(
        @NotNull
        Long initiatorId,
        @NotNull
        Long recipientId,
        @NotNull
        String chatName
) {
}
