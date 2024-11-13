package ru.expanse.schema;

import jakarta.validation.constraints.NotNull;
import ru.expanse.model.UserRole;

public record UpdateChatUserRequest(
        @NotNull Long chatId,
        @NotNull Long userId,
        @NotNull UserRole userRole
) {
}
