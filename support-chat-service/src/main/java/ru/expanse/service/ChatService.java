package ru.expanse.service;

import ru.expanse.schema.ChatEvent;
import ru.expanse.schema.ChatRecord;
import ru.expanse.schema.SaveChatRequest;

import java.time.OffsetDateTime;

public interface ChatService {
    ChatEvent saveChat(SaveChatRequest request);

    ChatRecord getChatWithMessages(
            Long chatId,
            OffsetDateTime messagesFrom,
            OffsetDateTime messagesTo
    );
}
