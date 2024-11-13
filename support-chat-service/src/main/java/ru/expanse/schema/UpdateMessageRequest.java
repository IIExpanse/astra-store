package ru.expanse.schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateMessageRequest(
        @NotNull Long id,
        @NotBlank String text
) {
}
