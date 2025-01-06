package ru.expanse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.expanse.schema.GetChatWithMessagesRequest;
import ru.expanse.schema.GetChatWithMessagesResponse;
import ru.expanse.service.ChatService;
import ru.expanse.util.DataProvider;

import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ChatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ChatService chatService;
    @LocalServerPort
    private Integer port;

    @Nested
    class RestTest {
        @Test
        @SneakyThrows
        void getChatWithMessages() {
            GetChatWithMessagesResponse response = getDefaultResponse();
            Mockito.when(chatService.getChatWithMessages(ArgumentMatchers.any(GetChatWithMessagesRequest.class)))
                    .thenReturn(response);

            mockMvc.perform(
                            get("/chats/messages")
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .content(objectMapper.writeValueAsString(getDefaultRequest()))
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(response)));
        }

        private GetChatWithMessagesRequest getDefaultRequest() {
            return new GetChatWithMessagesRequest(
                    1L,
                    OffsetDateTime.now(),
                    OffsetDateTime.now().plusMinutes(1)
            );
        }

        private GetChatWithMessagesResponse getDefaultResponse() {
            return new GetChatWithMessagesResponse(
                    1L,
                    "Name",
                    List.of(DataProvider.getDefaultMessageRecord())
            );
        }
    }
}
