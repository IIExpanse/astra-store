package ru.expanse.schema;

import java.time.OffsetDateTime;

public record MessageRecord(
        Long id,
        String text,
        OffsetDateTime timestamp,
        Long repliedTo,
        Long authorId
) {
}
