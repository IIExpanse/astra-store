package ru.expanse.dao.adapter;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.expanse.dao.repository.ChatUserRepository;
import ru.expanse.dao.repository.UserRepository;
import ru.expanse.model.Chat;
import ru.expanse.model.ChatUser;
import ru.expanse.model.ChatUserId;
import ru.expanse.model.User;
import ru.expanse.util.DataProvider;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ChatDaoAdapterTest {
    @Autowired
    private ChatDaoAdapter userDaoAdapter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatUserRepository chatUserRepository;
    @Autowired
    private ChatDaoAdapter chatDaoAdapter;

    @Nested
    class CrudTest {
        @Test
        void saveAndGet() {
            Chat chat = DataProvider.getDefaultChat();
            userDaoAdapter.save(chat);
            assertTrue(userDaoAdapter.getById(chat.getId()).isPresent());
        }
    }

    @Nested
    class CustomLogicTest {
        @Test
        void getChatsByFilter() {
            User user = DataProvider.getDefaultUser();
            user = userRepository.save(user);

            Chat chat1 = userDaoAdapter.save(DataProvider.getDefaultChat());
            ChatUserId chatUserId1 = new ChatUserId();
            chatUserId1.setUser(user);
            chatUserId1.setChat(chat1);
            ChatUser chatUser1 = DataProvider.getDefaultChatUser(chatUserId1);
            chatUserRepository.save(chatUser1);

            Chat chat2 = userDaoAdapter.save(DataProvider.getDefaultChat());
            ChatUserId chatUserId2 = new ChatUserId();
            chatUserId2.setUser(user);
            chatUserId2.setChat(chat2);
            ChatUser chatUser2 = DataProvider.getDefaultChatUser(chatUserId2);
            chatUserRepository.save(chatUser2);

            assertEquals(2, chatDaoAdapter.getChatsByFilter(null, null).size());
            assertEquals(2, userDaoAdapter.getChatsByFilter(user.getId(), null).size());
            assertEquals(1, userDaoAdapter.getChatsByFilter(user.getId(), List.of(chat1.getId())).size());
            assertEquals(1, userDaoAdapter.getChatsByFilter(null, List.of(chat1.getId())).size());
            assertEquals(0, userDaoAdapter.getChatsByFilter(Long.MAX_VALUE, null).size());
            assertEquals(0, userDaoAdapter.getChatsByFilter(null, List.of()).size());
            assertEquals(0, userDaoAdapter.getChatsByFilter(null, List.of(Long.MAX_VALUE)).size());
        }
    }
}