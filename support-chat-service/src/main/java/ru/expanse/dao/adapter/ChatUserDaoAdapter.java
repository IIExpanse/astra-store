package ru.expanse.dao.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.expanse.dao.repository.ChatRepository;
import ru.expanse.dao.repository.ChatUserRepository;
import ru.expanse.dao.repository.UserRepository;
import ru.expanse.exception.BusinessException;
import ru.expanse.exception.ExceptionCode;
import ru.expanse.mapper.ChatUserMapper;
import ru.expanse.model.Chat;
import ru.expanse.model.ChatUser;
import ru.expanse.model.ChatUserId;
import ru.expanse.model.User;
import ru.expanse.schema.UpdateChatUserRequest;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatUserDaoAdapter {
    private final ChatUserRepository chatUserRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatUserMapper chatUserMapper;

    public ChatUser save(ChatUser chatUser) {
        return chatUserRepository.save(chatUser);
    }

    public Optional<ChatUser> getById(Long chatId, Long userId) {
        return chatUserRepository.findById(getCompositeId(chatId, userId));
    }

    public ChatUser update(UpdateChatUserRequest request) {
        ChatUser chatUser = getById(request.chatId(), request.userId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.CHAT_USER_ASSOCIATION_NOT_FOUND));
        chatUser = chatUserMapper.updateModel(request, chatUser);
        return save(chatUser);
    }

    public boolean delete(Long chatId, Long userId) {
        ChatUserId id = getCompositeId(chatId, userId);
        if (chatUserRepository.findById(id).isEmpty()) {
            return false;
        }
        chatUserRepository.deleteById(id);
        return true;
    }

    private ChatUserId getCompositeId(Long chatId, Long userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.CHAT_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.USER_NOT_FOUND));

        ChatUserId id = new ChatUserId();
        id.setChat(chat);
        id.setUser(user);
        return id;
    }
}
