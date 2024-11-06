package ru.expanse.schema;

import java.time.ZonedDateTime;

public record MessageRecord(Long id, String text, ZonedDateTime timestamp, Long repliedTo, Long authorId) {
}
