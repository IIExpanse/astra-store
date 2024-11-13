package ru.expanse.service;

import ru.expanse.schema.DeleteMessageRequest;
import ru.expanse.schema.GetAllMessagesRequest;
import ru.expanse.schema.MessageEvent;
import ru.expanse.schema.MessageRecord;
import ru.expanse.schema.UpdateMessageRequest;

import java.util.List;

public interface MessageService {
    MessageEvent saveMessage(MessageRecord messageRecord);

    MessageRecord getMessage(Long id);

    List<MessageRecord> getAllMessages(GetAllMessagesRequest request);

    MessageEvent updateMessage(UpdateMessageRequest request);

    MessageEvent deleteMessage(DeleteMessageRequest request);
}
