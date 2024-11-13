package ru.expanse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.expanse.dao.adapter.MessageDaoAdapter;
import ru.expanse.dao.adapter.UserDaoAdapter;
import ru.expanse.exception.BusinessException;
import ru.expanse.exception.ExceptionCode;
import ru.expanse.mapper.MessageMapper;
import ru.expanse.model.Message;
import ru.expanse.model.User;
import ru.expanse.schema.DeleteMessageRequest;
import ru.expanse.schema.GetAllMessagesRequest;
import ru.expanse.schema.MessageAction;
import ru.expanse.schema.MessageEvent;
import ru.expanse.schema.MessageRecord;
import ru.expanse.schema.UpdateMessageRequest;
import ru.expanse.service.MessageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageDaoAdapter messageDaoAdapter;
    private final UserDaoAdapter userDaoAdapter;
    private final MessageMapper messageMapper;

    @Override
    public MessageEvent saveMessage(MessageRecord messageRecord) {
        User author = userDaoAdapter.getById(messageRecord.authorId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.USER_NOT_FOUND));

        Message repliedTo = null;
        if (messageRecord.repliedTo() != null) {
            repliedTo = messageDaoAdapter.getById(messageRecord.repliedTo())
                    .orElseThrow(() -> new BusinessException(ExceptionCode.MESSAGE_NOT_FOUND));
        }

        Message message = messageMapper.toModel(messageRecord, author, repliedTo);
        message = messageDaoAdapter.save(message);
        return new MessageEvent(message.getId(), message.getChat().getId(), MessageAction.CREATE);
    }

    @Override
    public MessageRecord getMessage(Long id) {
        return messageMapper.toRecord(messageDaoAdapter.getById(id)
                .orElseThrow(() -> new BusinessException(ExceptionCode.MESSAGE_NOT_FOUND))
        );
    }

    @Override
    public List<MessageRecord> getAllMessages(GetAllMessagesRequest request) {
        return messageDaoAdapter.getAll(request.from(), request.to()).stream()
                .map(messageMapper::toRecord)
                .toList();
    }

    @Override
    public MessageEvent updateMessage(UpdateMessageRequest request) {
        Message newMessage = messageMapper.toModel(request);
        newMessage = messageDaoAdapter.update(newMessage);
        return new MessageEvent(newMessage.getId(), newMessage.getChat().getId(), MessageAction.UPDATE);
    }

    @Override
    public MessageEvent deleteMessage(DeleteMessageRequest request) {
        Message message = messageDaoAdapter.getById(request.id())
                .orElseThrow(() -> new BusinessException(ExceptionCode.MESSAGE_NOT_FOUND));
        messageDaoAdapter.delete(request.id());
        return new MessageEvent(message.getId(), message.getChat().getId(), MessageAction.DELETE);
    }
}