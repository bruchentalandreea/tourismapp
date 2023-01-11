package com.example.springdemo.errorhandler;

public class HolidayNotFoundException extends RuntimeException {
    public HolidayNotFoundException(String message) {
        super(message);
    }
}