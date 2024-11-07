package ru.expanse.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(ExceptionCode code) {
        super(code.getMessage());
    }
}
