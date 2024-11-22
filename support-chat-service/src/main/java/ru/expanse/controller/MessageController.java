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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.expanse.schema.DeleteMessageRequest;
import ru.expanse.schema.GetAllMessagesRequest;
import ru.expanse.schema.MessageEvent;
import ru.expanse.schema.MessageRecord;
import ru.expanse.schema.SaveMessageRequest;
import ru.expanse.schema.UpdateMessageRequest;
import ru.expanse.service.MessageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate template;
    public static final String EVENTS_TOPIC = "/topic/message-events";

    @GetMapping("/message/{id}")
    public ResponseEntity<MessageRecord> getMessage(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessage(id));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageRecord>> getAllMessages(@Valid @RequestBody GetAllMessagesRequest request) {
        return ResponseEntity.ok(messageService.getAllMessages(request));
    }

    @MessageMapping("/message/create")
    public void postMessage(@Valid @Payload SaveMessageRequest request) {
        MessageEvent messageEvent = messageService.saveMessage(request);
        template.convertAndSend(getChatPath(messageEvent.chatId()), messageEvent);
    }

    @MessageMapping("/message/update")
    public void updateMessage(@Valid @Payload UpdateMessageRequest request) {
        MessageEvent messageEvent = messageService.updateMessage(request);
        template.convertAndSend(getChatPath(messageEvent.chatId()), messageEvent);
    }

    @MessageMapping("/message/delete")
    public void deleteMessage(@Valid @Payload DeleteMessageRequest request) {
        MessageEvent messageEvent = messageService.deleteMessage(request);
        template.convertAndSend(getChatPath(messageEvent.chatId()), messageEvent);
    }

    private String getChatPath(Long chatId) {
        return EVENTS_TOPIC + "/chat/" + chatId;
    }
}
