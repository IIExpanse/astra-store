package ru.expanse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.expanse.schema.GetAllMessagesRequest;
import ru.expanse.schema.MessageRecord;
import ru.expanse.service.MessageService;
import ru.expanse.util.DataProvider;

import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MessageService messageService;

    @Nested
    class RestTest {
        @Test
        @SneakyThrows
        void getMessage() {
            MessageRecord record = DataProvider.getDefaultMessageRecord();
            when(messageService.getMessage(ArgumentMatchers.anyLong()))
                    .thenReturn(record);

            mockMvc.perform(
                            get("/message/1")
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(record)));
        }

        @Test
        @SneakyThrows
        void getAllMessages() {
            List<MessageRecord> list = List.of(DataProvider.getDefaultMessageRecord());
            when(messageService.getAllMessages(ArgumentMatchers.any(GetAllMessagesRequest.class)))
                    .thenReturn(list);
            OffsetDateTime now = OffsetDateTime.now();

            mockMvc.perform(
                            get("/messages")
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .content(objectMapper.writeValueAsString(new GetAllMessagesRequest(now, now)))
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(list)));
        }
    }

    @Nested
    class WebSocketTest {
        @Test
        void postMessage() {

        }

        @Test
        void updateMessage() {
        }

        @Test
        void deleteMessage() {
        }
    }
}