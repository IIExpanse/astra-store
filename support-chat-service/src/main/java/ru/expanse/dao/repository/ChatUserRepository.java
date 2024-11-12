package ru.expanse.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.expanse.model.ChatUser;
import ru.expanse.model.ChatUserId;

public interface ChatUserRepository extends JpaRepository<ChatUser, ChatUserId> {
}
