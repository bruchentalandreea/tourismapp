package com.example.springdemo.dto;


import javax.persistence.Column;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class PostDTO {
    private Long id;
    private String content;
    private String title;
    private String username;
    private Double price;
    private LocalDate check_in_date;
    private LocalDate check_out_date;


    private String city;
    private String state;
    private String country;

    private String photoId;
    private Double avgRating;
    private String listPhotoId;

    private Integer nr_of_persons;
    private String type_of_vacation;


    private String createdOn;
    private String updatedOn;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public LocalDate getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(LocalDate check_in_date) {
        this.check_in_date = check_in_date;
    }

    public LocalDate getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(LocalDate check_out_date) {
        this.check_out_date = check_out_date;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public String getListPhotoId() {
        return listPhotoId;
    }

    public void setListPhotoId(String listPhotoId) {
        this.listPhotoId = listPhotoId;
    }

    public Integer getNr_of_persons() {
        return nr_of_persons;
    }

    public void setNr_of_persons(Integer nr_of_persons) {
        this.nr_of_persons = nr_of_persons;
    }

    public String getType_of_vacation() {
        return type_of_vacation;
    }

    public void setType_of_vacation(String type_of_vacation) {
        this.type_of_vacation = type_of_vacation;
    }


    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }
}