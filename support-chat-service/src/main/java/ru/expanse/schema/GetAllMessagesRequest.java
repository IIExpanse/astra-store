package ru.expanse.schema;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record GetAllMessagesRequest(
        @NotNull OffsetDateTime from,
        @NotNull OffsetDateTime to
) {
}
