package ru.expanse.schema;

import jakarta.validation.constraints.NotNull;

public record DeleteMessageRequest(@NotNull Long id) {
}
