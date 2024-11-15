package ru.expanse.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import ru.expanse.dao.adapter.ChatDaoAdapter;
import ru.expanse.dao.adapter.MessageDaoAdapter;
import ru.expanse.dao.adapter.UserDaoAdapter;
import ru.expanse.exception.BusinessException;
import ru.expanse.exception.ExceptionCode;
import ru.expanse.mapper.MessageMapper;
import ru.expanse.model.Chat;
import ru.expanse.model.User;
import ru.expanse.schema.SaveMessageRequest;
import ru.expanse.service.MessageService;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MessageServiceImplTest {
    private MessageService messageService;
    private MessageDaoAdapter messageDaoAdapter;
    private UserDaoAdapter userDaoAdapter;
    private ChatDaoAdapter chatDaoAdapter;
    private MessageMapper messageMapper;

    @BeforeEach
    void setUp() {
        messageDaoAdapter = Mockito.mock(MessageDaoAdapter.class);
        userDaoAdapter = Mockito.mock(UserDaoAdapter.class);
        chatDaoAdapter = Mockito.mock(ChatDaoAdapter.class);
        messageMapper = Mappers.getMapper(MessageMapper.class);
        messageService = new MessageServiceImpl(
                messageDaoAdapter, userDaoAdapter, chatDaoAdapter, messageMapper
        );
    }

    @Nested
    class ExceptionTest {
        @Test
        void saveMessage_shouldThrowExceptionForNotFoundEntities() {
            SaveMessageRequest request = new SaveMessageRequest(
                    "abc",
                    OffsetDateTime.now(),
                    1L,
                    1L,
                    1L
            );
            when(userDaoAdapter.getById(request.authorId()))
                    .thenReturn(Optional.empty());
            Exception e = assertThrows(BusinessException.class, () -> messageService.saveMessage(request));
            assertEquals(ExceptionCode.USER_NOT_FOUND.getMessage(), e.getMessage());
            when(userDaoAdapter.getById(request.authorId()))
                    .thenReturn(Optional.of(new User()));

            when(chatDaoAdapter.getById(request.chatId()))
                    .thenReturn(Optional.empty());
            e = assertThrows(BusinessException.class, () -> messageService.saveMessage(request));
            assertEquals(ExceptionCode.CHAT_NOT_FOUND.getMessage(), e.getMessage());
            when(chatDaoAdapter.getById(request.authorId()))
                    .thenReturn(Optional.of(new Chat()));

            when(messageDaoAdapter.getById(request.repliedTo()))
                    .thenReturn(Optional.empty());
            e = assertThrows(BusinessException.class, () -> messageService.saveMessage(request));
            assertEquals(ExceptionCode.MESSAGE_NOT_FOUND.getMessage(), e.getMessage());
        }
    }
}