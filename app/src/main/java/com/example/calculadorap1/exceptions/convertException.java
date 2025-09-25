package com.example.calculadorap1.exceptions;

public class convertException extends RuntimeException {

    public convertException(String message) {
        super(message);
    }

    public convertException(String message, Throwable cause) {
        super(message, cause);
    }

}
