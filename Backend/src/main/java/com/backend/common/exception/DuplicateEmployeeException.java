package com.backend.common.exception;

public class DuplicateEmployeeException extends RuntimeException {

    public DuplicateEmployeeException(String message) {
        super(message);
    }

    public DuplicateEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
}
