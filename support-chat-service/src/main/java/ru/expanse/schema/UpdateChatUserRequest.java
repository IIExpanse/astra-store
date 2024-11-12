package ru.expanse.schema;

import ru.expanse.model.UserRole;

public record UpdateChatUserRequest(Long chatId, Long userId, UserRole userRole) {
}
