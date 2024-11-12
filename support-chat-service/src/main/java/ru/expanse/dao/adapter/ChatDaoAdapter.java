package ru.expanse.dao.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.expanse.dao.repository.ChatRepository;
import ru.expanse.model.Chat;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatDaoAdapter {
    private final ChatRepository chatRepository;

    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    public Optional<Chat> getById(Long id) {
        return chatRepository.findById(id);
    }
}
