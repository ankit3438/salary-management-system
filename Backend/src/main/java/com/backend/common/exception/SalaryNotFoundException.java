package com.backend.common.exception;

public class SalaryNotFoundException extends RuntimeException {

    public SalaryNotFoundException(String message) {
        super(message);
    }

    public SalaryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
