package ru.expanse.schema;

import java.util.List;

public record ChatRecord(
        Long id,
        String name,
        List<MessageRecord> messages
) {
}
