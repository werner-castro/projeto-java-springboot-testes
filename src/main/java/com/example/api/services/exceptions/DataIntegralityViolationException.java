package com.example.api.services.exceptions;

public class DataIntegralityViolationException extends RuntimeException{

    public DataIntegralityViolationException(String message) {
        super(message);
    }
}
