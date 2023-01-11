package com.example.springdemo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {

    private Long feedback_id;
    private Integer rating;
    private Long post_id;
    private String username;
    private Long holiday_id;
}
