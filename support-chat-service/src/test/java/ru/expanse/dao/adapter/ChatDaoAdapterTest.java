package ru.expanse.dao.adapter;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.expanse.model.Chat;
import ru.expanse.util.DataProvider;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ChatDaoAdapterTest {
    @Autowired
    private ChatDaoAdapter chatRepository;

    @Nested
    class CrudTest {
        @Test
        void saveAndGet() {
            Chat chat = DataProvider.getDefaultChat();
            chatRepository.save(chat);
            assertTrue(chatRepository.getById(chat.getId()).isPresent());
        }
    }
}