package com.example.springdemo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column
    private String title;
    @Column
    @NotBlank
    private String bookedBy;
    @Column
    private Double price;


    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String country;
    @Column
    private String photoId;
    @Column
    private Double avgRating;

    //Timestamp was before
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate check_in_date;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate check_out_date;

    @Column
    private Integer nr_of_persons;
    @Column
    private String type_of_vacation;
    @Column
    private Long holiday_id;
    @Column
    private Long post_id;

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