package ru.expanse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import ru.expanse.schema.*;
import ru.expanse.service.ChatService;
import ru.expanse.util.DataProvider;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Nested
    class WebSocketTest {
        private BlockingQueue<ChatEvent> blockingQueue;
        private StompSession session;

        @BeforeEach
        @SneakyThrows
        public void setup() {
            WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(
                    List.of(new WebSocketTransport(new StandardWebSocketClient()))));
            blockingQueue = new LinkedBlockingDeque<>();

            stompClient.setMessageConverter(new MappingJackson2MessageConverter(objectMapper));
            session = stompClient.connectAsync(DataProvider.getWsPath(port), new DefaultStompSessionHandler())
                    .get(1, TimeUnit.SECONDS);
        }

        @AfterEach
        public void disconnect() {
            session.disconnect();
        }

        @Test
        @SneakyThrows
        void createChat() {
            ChatEvent event = new ChatEvent(1L, 1L, ChatAction.CREATE);
            Mockito.when(chatService.saveChat(ArgumentMatchers.any(SaveChatRequest.class)))
                    .thenReturn(event);
            session.subscribe(ChatController.CHAT_EVENTS_TOPIC, new DefaultStompSessionHandler());

            SaveChatRequest request = DataProvider.getDefaultSaveChatRequest();

            session.send("/request/chat/create", request);
            assertEquals(event, blockingQueue.poll(1, TimeUnit.SECONDS));
        }

        @SuppressWarnings("NullableProblems")
        class DefaultStompSessionHandler extends StompSessionHandlerAdapter {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return ChatEvent.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                blockingQueue.add((ChatEvent) o);
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                throw new AssertionError(exception);
            }
        }
    }
}
