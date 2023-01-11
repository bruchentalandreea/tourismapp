package com.example.springdemo.dto;

import lombok.Data;

import java.io.Serializable;

public class CategoryDTO  {

    private Long id;
    private String category;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
