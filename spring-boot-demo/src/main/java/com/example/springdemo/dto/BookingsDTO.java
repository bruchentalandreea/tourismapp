package com.example.springdemo.dto;


import java.time.LocalDate;


public class BookingsDTO {

    private Long id;
    private String title;
    private String bookedBy;
    private Double price;
    private String city;
    private String state;
    private String country;
    private String photoId;
    private Double avgRating;
    private LocalDate check_in_date;
    private LocalDate check_out_date;
    private Integer nr_of_persons;
    private String type_of_vacation;
    private Long holiday_id;
    private Long post_id;

    public BookingsDTO() { }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Long getHoliday_id() {
        return holiday_id;
    }

    public void setHoliday_id(Long holiday_id) {
        this.holiday_id = holiday_id;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }
}
