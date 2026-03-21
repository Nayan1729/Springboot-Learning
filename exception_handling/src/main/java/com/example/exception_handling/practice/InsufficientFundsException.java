package com.example.exception_handling.practice;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Exception e) {
        super(message, e);
    }
}
