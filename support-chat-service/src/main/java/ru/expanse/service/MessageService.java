package ru.expanse.service;

import ru.expanse.schema.DeleteMessageRequest;
import ru.expanse.schema.GetMessagesByFilterRequest;
import ru.expanse.schema.MessageEvent;
import ru.expanse.schema.MessageRecord;
import ru.expanse.schema.SaveMessageRequest;
import ru.expanse.schema.UpdateMessageRequest;

import java.util.List;

public interface MessageService {
    MessageEvent saveMessage(SaveMessageRequest request);

    MessageRecord getMessage(Long id);

    List<MessageRecord> getMessagesByFilter(GetMessagesByFilterRequest request);

    MessageEvent updateMessage(UpdateMessageRequest request);

    MessageEvent deleteMessage(DeleteMessageRequest request);
}
