package com.example.springdemo.controller;


import com.example.springdemo.dto.FeedbackDTO;
import com.example.springdemo.services.FeedbackService;
import com.example.springdemo.services.HolidayService;
import com.example.springdemo.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final PostService postService;
    private final HolidayService holidayService;

    public FeedbackController(FeedbackService feedbackService, PostService postService, HolidayService holidayService) {

        this.feedbackService = feedbackService;
        this.postService = postService;
        this.holidayService = holidayService;
    }

    @PostMapping
    public ResponseEntity createFeedback(@RequestBody FeedbackDTO feedbackDTO) {

        feedbackService.createFeedback(feedbackDTO);
        Double currentAvg = postService.getAvgRating(feedbackDTO.getPost_id());
        Double newAvg = (double) (currentAvg + feedbackDTO.getRating()) / 2;
        postService.updatePost(newAvg, feedbackDTO.getPost_id());

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/holiday")
    public ResponseEntity createFeedbackForHoliday(@RequestBody FeedbackDTO feedbackDTO) {

        feedbackService.createFeedbackForHoliday(feedbackDTO);
        Double currentAvg = holidayService.getAvgRating(feedbackDTO.getHoliday_id());
        Double newAvg = (double) (currentAvg + feedbackDTO.getRating()) / 2;
        holidayService.updateHoliday(newAvg, feedbackDTO.getHoliday_id());

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeedbackDTO>> showAllFeebacks() {
        return new ResponseEntity<>(feedbackService.showAllFeedbacks(), HttpStatus.OK);
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List< FeedbackDTO>> getFeedbacksByUsername(@PathVariable String name) {
        return status(HttpStatus.OK).body( feedbackService.getFeedbacksByUsername(name));
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List< FeedbackDTO>> getFeedbacksByPost(@PathVariable Long postId) {
        return status(HttpStatus.OK).body( feedbackService.getFeedbacksByPost(postId));
    }
    @GetMapping("/by-holiday/{holidayId}")
    public ResponseEntity<List< FeedbackDTO>> getFeedbacksByHoliday(@PathVariable Long holidayId) {
        return status(HttpStatus.OK).body( feedbackService.getFeedbacksByHoliday(holidayId));
    }
}
