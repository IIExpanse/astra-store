package ru.expanse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import ru.expanse.schema.GetAllMessagesRequest;
import ru.expanse.schema.MessageAction;
import ru.expanse.schema.MessageEvent;
import ru.expanse.schema.MessageRecord;
import ru.expanse.schema.SaveMessageRequest;
import ru.expanse.service.MessageService;
import ru.expanse.util.DataProvider;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MessageService messageService;
    @LocalServerPort
    private Integer port;

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
        private BlockingQueue<MessageEvent> blockingQueue;
        private WebSocketStompClient stompClient;
        private StompSession session;

        @BeforeEach
        @SneakyThrows
        public void setup() {

            blockingQueue = new LinkedBlockingDeque<>();
            stompClient = new WebSocketStompClient(new SockJsClient(
                    List.of(new WebSocketTransport(new StandardWebSocketClient()))));

            stompClient.setMessageConverter(new MappingJackson2MessageConverter(objectMapper));
            session = stompClient.connectAsync(getWsPath(), new DefaultStompSessionHandler())
                    .get(1, TimeUnit.SECONDS);
        }

        @AfterEach
        public void disconnect() {
            session.disconnect();
        }

        @Test
        @SneakyThrows
        void postMessage() {
            MessageEvent event = new MessageEvent(1L, 2L, MessageAction.CREATE);
            Mockito.when(messageService.saveMessage(ArgumentMatchers.any(SaveMessageRequest.class)))
                            .thenReturn(event);
            session.subscribe(MessageController.EVENTS_TOPIC, new DefaultStompSessionHandler());

            SaveMessageRequest request = DataProvider.getDefaultSaveMessageRequest();

            session.send("/ws-request/message/new", request);
            assertEquals(event, blockingQueue.poll(1, TimeUnit.SECONDS));
        }

        @Test
        void updateMessage() {
        }

        @Test
        void deleteMessage() {
        }

        class DefaultStompSessionHandler extends StompSessionHandlerAdapter {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return MessageEvent.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                blockingQueue.offer((MessageEvent) o);
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                exception.printStackTrace();
            }
        }

        private String getWsPath() {
            return String.format("ws://localhost:%d/ws", port);
        }
    }
}