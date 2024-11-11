package ru.expanse.dao.adapter;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.expanse.dao.repository.UserRepository;
import ru.expanse.model.Message;
import ru.expanse.model.User;
import ru.expanse.schema.UpdateMessageRequest;
import ru.expanse.util.DataProvider;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MessageDaoAdapterTest {
    @Autowired
    private MessageDaoAdapter messageDaoAdapter;
    @Autowired
    private UserRepository userRepository;

    @Nested
    class CrudTest {
        @Test
        void saveAndGet() {
            Message message = saveDefaultMessage();
            assertTrue(messageDaoAdapter.getById(message.getId()).isPresent());
        }

        @Test
        void update() {
            Message message = saveDefaultMessage();
            UpdateMessageRequest request = new UpdateMessageRequest(message.getId(), "new text");
            message = messageDaoAdapter.update(request);
            assertEquals(messageDaoAdapter.getById(message.getId()).orElseThrow().getText(), request.text());
        }

        @Test
        void delete() {
            Message message = saveDefaultMessage();
            assertTrue(messageDaoAdapter.delete(message.getId()));
            assertFalse(messageDaoAdapter.delete(message.getId()));
            assertFalse(messageDaoAdapter.getById(message.getId()).isPresent());
        }
    }

    private Message saveDefaultMessage() {
        User user = saveDefaultUser();
        Message message = DataProvider.getMessage(user);
        return messageDaoAdapter.save(message);
    }

    private User saveDefaultUser() {
        User user = DataProvider.getUser();
        return userRepository.save(user);
    }
}