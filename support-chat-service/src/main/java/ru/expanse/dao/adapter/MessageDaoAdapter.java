package ru.expanse.dao.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.expanse.dao.repository.MessageRepository;
import ru.expanse.exception.BusinessException;
import ru.expanse.exception.ExceptionCode;
import ru.expanse.mapper.MessageMapper;
import ru.expanse.model.Message;
import ru.expanse.schema.UpdateMessageRequest;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageDaoAdapter {
    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public Optional<Message> getById(Long id) {
        return messageRepository.findById(id);
    }

    public Message update(UpdateMessageRequest request) {
        Message message = getById(request.id())
                .orElseThrow(() -> new BusinessException(ExceptionCode.MESSAGE_NOT_FOUND));
        message = messageMapper.updateModel(request, message);
        return save(message);
    }

    public boolean delete(Long id) {
        if (messageRepository.findById(id).isEmpty()) {
            return false;
        }
        messageRepository.deleteById(id);
        return true;
    }
}
