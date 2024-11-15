package ru.expanse.schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record SaveMessageRequest (
    @NotBlank
    String text,
    @NotNull
    OffsetDateTime timestamp,
    Long repliedTo,
    @NotNull
    Long authorId
) {}
