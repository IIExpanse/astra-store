package ru.expanse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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
    public static final String EVENTS_TOPIC = "/topic/message-events";

    @GetMapping("/message/{id}")
    public ResponseEntity<MessageRecord> getMessage(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessage(id));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageRecord>> getAllMessages(@Valid @RequestBody GetAllMessagesRequest request) {
        return ResponseEntity.ok(messageService.getAllMessages(request));
    }

    @MessageMapping("/message/new")
    @SendTo(EVENTS_TOPIC)
    public MessageEvent postMessage(@Valid @Payload SaveMessageRequest request) {
        return messageService.saveMessage(request);
    }

    @MessageMapping("/message/edit")
    @SendTo(EVENTS_TOPIC)
    public MessageEvent updateMessage(@Valid @Payload UpdateMessageRequest record) {
        return messageService.updateMessage(record);
    }

    @MessageMapping("/message/delete")
    @SendTo(EVENTS_TOPIC)
    public MessageEvent deleteMessage(@Valid @Payload DeleteMessageRequest record) {
        return messageService.deleteMessage(record);
    }
}
