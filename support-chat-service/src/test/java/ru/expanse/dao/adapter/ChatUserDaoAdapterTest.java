package ru.expanse.dao.adapter;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.expanse.dao.repository.ChatRepository;
import ru.expanse.dao.repository.UserRepository;
import ru.expanse.model.Chat;
import ru.expanse.model.ChatUser;
import ru.expanse.model.ChatUserId;
import ru.expanse.model.User;
import ru.expanse.model.UserRole;
import ru.expanse.util.DataProvider;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ChatUserDaoAdapterTest {
    @Autowired
    private ChatUserDaoAdapter chatUserDaoAdapter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    @Nested
    class CrudTest {
        @Test
        void saveAndGet() {
            ChatUser chatUser = saveDefaultChatUser();
            ChatUserId id = chatUser.getChatUserId();
            assertTrue(chatUserDaoAdapter.getById(
                    id.getChat().getId(),
                    id.getUser().getId()
            ).isPresent());
        }

        @Test
        void update() {
            ChatUser chatUser = saveDefaultChatUser();
            Long chatId = chatUser.getChatUserId().getChat().getId();
            Long userId = chatUser.getChatUserId().getUser().getId();

            ChatUser newChatUser = new ChatUser();
            newChatUser.setChatUserId(chatUser.getChatUserId());
            newChatUser.setUserRole(UserRole.USER);

            chatUserDaoAdapter.update(newChatUser);
            chatUser = chatUserDaoAdapter.getById(
                            chatId,
                            userId)
                    .orElseThrow();

            assertEquals(newChatUser.getUserRole(), chatUser.getUserRole());
        }

        @Test
        void delete() {
            ChatUser chatUser = saveDefaultChatUser();
            Long chatId = chatUser.getChatUserId().getChat().getId();
            Long userId = chatUser.getChatUserId().getUser().getId();

            chatUserDaoAdapter.delete(chatId, userId);
            assertTrue(chatUserDaoAdapter.getById(chatId, userId).isEmpty());
        }
    }

    private ChatUser saveDefaultChatUser() {
        User user = saveDefaultUser();
        Chat chat = saveDefaultChat();
        ChatUserId id = new ChatUserId();
        id.setChat(chat);
        id.setUser(user);

        ChatUser chatUser = new ChatUser();
        chatUser.setChatUserId(id);
        chatUser.setUserRole(UserRole.ADMIN);
        return chatUserDaoAdapter.save(chatUser);
    }

    private User saveDefaultUser() {
        User user = DataProvider.getDefaultUser();
        return userRepository.save(user);
    }

    private Chat saveDefaultChat() {
        Chat chat = DataProvider.getDefaultChat();
        return chatRepository.save(chat);
    }
}