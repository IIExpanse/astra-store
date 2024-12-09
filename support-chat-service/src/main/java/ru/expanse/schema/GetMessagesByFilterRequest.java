package ru.expanse.schema;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;

public record GetMessagesByFilterRequest(
        @NotNull List<Long> chatIds,
        @NotNull OffsetDateTime from,
        @NotNull OffsetDateTime to
) {
}
