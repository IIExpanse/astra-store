package ru.expanse.service;

import ru.expanse.schema.ChatEvent;
import ru.expanse.schema.GetChatWithMessagesRequest;
import ru.expanse.schema.GetChatWithMessagesResponse;
import ru.expanse.schema.SaveChatRequest;

public interface ChatService {
    ChatEvent saveChat(SaveChatRequest request);

    GetChatWithMessagesResponse getChatWithMessages(GetChatWithMessagesRequest request);
}
