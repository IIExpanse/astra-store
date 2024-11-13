package ru.expanse.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.expanse.model.Message;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByTimestampGreaterThanEqualAndTimestampLessThanEqual(OffsetDateTime start, OffsetDateTime end);
}
