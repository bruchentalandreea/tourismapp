package com.example.springdemo.errorhandler;

public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}
