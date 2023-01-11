package com.example.springdemo.dto;

import com.example.springdemo.entities.Category;

import java.time.Instant;

public class WalletDTO {

    private Long id;
    private Integer amount;
    private Instant date_wallet;
    private String username;
    private String category;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate_wallet() {
        return date_wallet;
    }

    public void setDate_wallet(Instant date_wallet) {
        this.date_wallet = date_wallet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
