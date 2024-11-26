package ru.expanse.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.expanse.model.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("""
            SELECT c
            FROM Chat c
            JOIN ChatUser cu ON cu.chatUserId.chat.id = c.id
            WHERE (:userId IS NULL OR cu.chatUserId.user.id = :userId)
            AND (:chatIds IS NULL OR c.id IN :chatIds)
            """)
    List<Chat> getChatsByFilter(Long userId, List<Long> chatIds);
}
