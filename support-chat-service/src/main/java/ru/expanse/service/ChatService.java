package ru.expanse.service;

import ru.expanse.schema.ChatEvent;
import ru.expanse.schema.ChatRecord;
import ru.expanse.schema.SaveChatRequest;

import java.time.OffsetDateTime;
import java.util.List;

public interface ChatService {
    ChatEvent saveChat(SaveChatRequest request);

    ChatRecord getChatsByFilter(
            Long userId,
            List<Long> chatIds,
            OffsetDateTime messagesFrom,
            OffsetDateTime messagesTo
    );
}
