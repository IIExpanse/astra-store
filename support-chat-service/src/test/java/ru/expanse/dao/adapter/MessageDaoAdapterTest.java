package ru.expanse.dao.adapter;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.expanse.dao.repository.ChatRepository;
import ru.expanse.dao.repository.UserRepository;
import ru.expanse.exception.BusinessException;
import ru.expanse.exception.ExceptionCode;
import ru.expanse.mapper.MessageMapper;
import ru.expanse.model.Chat;
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
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageMapper messageMapper;

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
            message = messageDaoAdapter.update(messageMapper.toModel(request));
            assertEquals(messageDaoAdapter.getById(message.getId()).orElseThrow().getText(), request.text());
        }

        @Test
        void delete() {
            Message message = saveDefaultMessage();
            messageDaoAdapter.delete(message.getId());
            assertTrue(messageDaoAdapter.getById(message.getId()).isEmpty());
        }
    }

    @Nested
    class ExceptionsTest {
        @Test
        void throwsExceptionForNotFoundMessage() {
            Message message = new Message();
            message.setId(1L);
            Exception e = assertThrows(BusinessException.class, () -> messageDaoAdapter.update(message));
            assertEquals(e.getMessage(), ExceptionCode.MESSAGE_NOT_FOUND.getMessage());
        }
    }

    private Message saveDefaultMessage() {
        User user = saveDefaultUser();
        Chat chat = saveDefaultChat();
        Message message = DataProvider.getDefaultMessage(user, chat);
        return messageDaoAdapter.save(message);
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