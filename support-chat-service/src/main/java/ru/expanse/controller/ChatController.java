package ru.expanse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.expanse.schema.ChatEvent;
import ru.expanse.schema.GetChatWithMessagesRequest;
import ru.expanse.schema.GetChatWithMessagesResponse;
import ru.expanse.schema.SaveChatRequest;
import ru.expanse.service.ChatService;

@Controller
@RequiredArgsConstructor
@Validated
public class ChatController {
    private final ChatService messageService;
    private final SimpMessagingTemplate template;
    public static final String EVENTS_TOPIC = "/topic/chat-events";

    @GetMapping("/chats/messages")
    public ResponseEntity<GetChatWithMessagesResponse> getChatWithMessages(@Valid @RequestBody GetChatWithMessagesRequest request) {
        return ResponseEntity.ok(messageService.getChatWithMessages(request));
    }

    @MessageMapping("/chat/create")
    public void createChat(@Valid @Payload SaveChatRequest request) {
        ChatEvent chatEvent = messageService.saveChat(request);
        template.convertAndSend(EVENTS_TOPIC, chatEvent);
    }
}
