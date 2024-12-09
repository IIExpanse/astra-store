package ru.expanse.dao.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.expanse.dao.repository.MessageRepository;
import ru.expanse.exception.BusinessException;
import ru.expanse.exception.ExceptionCode;
import ru.expanse.mapper.MessageMapper;
import ru.expanse.model.Message;

import java.time.OffsetDateTime;
import java.util.List;
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

    public List<Message> getMessagesByFilter(List<Long> chatIds, OffsetDateTime from, OffsetDateTime to) {
        return messageRepository.getMessagesByFilter(chatIds, from, to);
    }

    public Message update(Message newMessage) {
        Message message = getById(newMessage.getId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.MESSAGE_NOT_FOUND));
        message = messageMapper.updateModel(newMessage, message);
        return save(message);
    }

    public void delete(Long id) {
        messageRepository.deleteById(id);
    }
}
