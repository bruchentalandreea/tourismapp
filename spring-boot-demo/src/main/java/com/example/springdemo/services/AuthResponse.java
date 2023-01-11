package com.example.springdemo.services;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String authenticationToken;
    private String username;
}