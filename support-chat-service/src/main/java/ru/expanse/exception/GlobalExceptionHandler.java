package ru.expanse.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            BusinessException.class,
            ConstraintViolationException.class
    })
    ResponseEntity<ErrorResponse> handleValidationExceptions(final Exception e) {
        log.debug(e.getMessage());

        return new ResponseEntity<>(
                new ErrorResponse(e.getClass().getSimpleName(), e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    public record ErrorResponse(String errorName, String error) {
    }
}
