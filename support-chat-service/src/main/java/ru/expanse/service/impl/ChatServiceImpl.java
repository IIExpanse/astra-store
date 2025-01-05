package ru.expanse.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.expanse.dao.adapter.ChatDaoAdapter;
import ru.expanse.dao.adapter.ChatUserDaoAdapter;
import ru.expanse.dao.adapter.MessageDaoAdapter;
import ru.expanse.dao.adapter.UserDaoAdapter;
import ru.expanse.exception.BusinessException;
import ru.expanse.exception.ExceptionCode;
import ru.expanse.mapper.MessageMapper;
import ru.expanse.model.Chat;
import ru.expanse.model.ChatUser;
import ru.expanse.model.ChatUserId;
import ru.expanse.model.User;
import ru.expanse.model.UserRole;
import ru.expanse.schema.ChatAction;
import ru.expanse.schema.ChatEvent;
import ru.expanse.schema.GetChatWithMessagesRequest;
import ru.expanse.schema.GetChatWithMessagesResponse;
import ru.expanse.schema.MessageRecord;
import ru.expanse.schema.SaveChatRequest;
import ru.expanse.service.ChatService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatDaoAdapter chatDaoAdapter;
    private final ChatUserDaoAdapter chatUserDaoAdapter;
    private final MessageDaoAdapter messageDaoAdapter;
    private final UserDaoAdapter userDaoAdapter;
    private final MessageMapper messageMapper;

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
    public GetChatWithMessagesResponse getChatWithMessages(GetChatWithMessagesRequest request) {
        Chat chat = chatDaoAdapter.getById(request.chatId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.CHAT_NOT_FOUND));

        List<MessageRecord> messageRecords = messageDaoAdapter.getMessagesByFilter(
                        List.of(request.chatId()),
                        request.from(),
                        request.to()
                ).stream()
                .map(messageMapper::toRecord)
                .toList();

        return new GetChatWithMessagesResponse(
                chat.getId(),
                chat.getName(),
                messageRecords
        );
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
