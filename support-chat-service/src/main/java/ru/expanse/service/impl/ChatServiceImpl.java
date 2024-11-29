package ru.expanse.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ru.expanse.dao.adapter.ChatDaoAdapter;
import ru.expanse.dao.adapter.ChatUserDaoAdapter;
import ru.expanse.dao.adapter.MessageDaoAdapter;
import ru.expanse.dao.adapter.UserDaoAdapter;
import ru.expanse.exception.BusinessException;
import ru.expanse.exception.ExceptionCode;
import ru.expanse.model.Chat;
import ru.expanse.model.ChatUser;
import ru.expanse.model.ChatUserId;
import ru.expanse.model.User;
import ru.expanse.model.UserRole;
import ru.expanse.schema.ChatAction;
import ru.expanse.schema.ChatEvent;
import ru.expanse.schema.ChatRecord;
import ru.expanse.schema.SaveChatRequest;
import ru.expanse.service.ChatService;

import java.time.OffsetDateTime;

@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatDaoAdapter chatDaoAdapter;
    private final ChatUserDaoAdapter chatUserDaoAdapter;
    private final MessageDaoAdapter messageDaoAdapter;
    private final UserDaoAdapter userDaoAdapter;

    @Override
    @Transactional
    public ChatEvent saveChat(SaveChatRequest request) {
        User initiator = userDaoAdapter.getById(request.initiatorId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.USER_NOT_FOUND));
        User recipient = userDaoAdapter.getById(request.recipientId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.USER_NOT_FOUND));

        Chat chat = new Chat();
        chat.setName(request.chatName());
        chat = chatDaoAdapter.save(chat);

        chatUserDaoAdapter.save(constructChatUserEntity(chat, initiator, UserRole.ADMIN));
        chatUserDaoAdapter.save(constructChatUserEntity(chat, recipient, UserRole.ADMIN));

        return new ChatEvent(chat.getId(), ChatAction.CREATE);
    }

    @Override
    public ChatRecord getChatWithMessages(Long chatId, OffsetDateTime messagesFrom, OffsetDateTime messagesTo) {
        return null;
    }

    private ChatUser constructChatUserEntity(Chat chat, User user, UserRole userRole) {
        ChatUserId id = new ChatUserId();
        id.setChat(chat);
        id.setUser(user);
        ChatUser chatUser = new ChatUser();
        chatUser.setChatUserId(id);
        chatUser.setUserRole(userRole);
        return chatUser;
    }
}
