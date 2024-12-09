package ru.expanse.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.expanse.model.Message;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("""
            SELECT m FROM Message m
            WHERE m.chat.id IN :chatIds
            AND m.timestamp >= :from
            AND m.timestamp <= :to
            """
    )
    List<Message> getMessagesByFilter(List<Long> chatIds, OffsetDateTime from, OffsetDateTime to);
}
