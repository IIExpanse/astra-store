package ru.expanse.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.expanse.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
