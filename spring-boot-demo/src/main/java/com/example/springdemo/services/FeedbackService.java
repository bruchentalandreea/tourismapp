package com.example.springdemo.services;

import com.example.springdemo.dto.FeedbackDTO;
import com.example.springdemo.entities.Feedback;
import com.example.springdemo.entities.Holiday;
import com.example.springdemo.entities.UserSimple;
import com.example.springdemo.repositories.FeedbackRepository;
import com.example.springdemo.repositories.HolidayRepository;
import com.example.springdemo.repositories.PostRepository;
import com.example.springdemo.repositories.UserSimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service

public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserSimpleRepository userSimpleRepository;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final HolidayRepository holidayRepository;

    private FeedbackDTO feedbackDTO;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, UserSimpleRepository userSimpleRepository, AuthService authService, PostRepository postRepository, HolidayRepository holidayRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userSimpleRepository = userSimpleRepository;
        this.authService = authService;
        this.postRepository = postRepository;
        this.holidayRepository = holidayRepository;
    }

    @Transactional
    public List<FeedbackDTO> showAllFeedbacks() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        return feedbackList.stream().map(this::mapFromFeedbackToDto).collect(toList());
    }

    @Transactional
    public void createFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = mapFromDtoToFeedback(feedbackDTO);
        feedbackRepository.save(feedback);
    }

    @Transactional
    public void createFeedbackForHoliday(FeedbackDTO feedbackDTO) {
        Feedback feedback = mapFromDtoToFeedbackForHoliday(feedbackDTO);
        feedbackRepository.save(feedback);
    }


    @Transactional(readOnly = true)
    public List<FeedbackDTO> getFeedbacksByUsername(String username) {
        UserSimple userSimple = userSimpleRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return feedbackRepository.filterByUser(userSimple.getUserName()).stream().map(this::mapFromFeedbackToDto).collect(toList());

    }


    @Transactional(readOnly = true)
    public List<Double> getFeedbacksByUsername2(String username) {
        UserSimple userSimple = userSimpleRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return feedbackRepository.filterByUser2(userSimple.getUserName());

    }

    @Transactional(readOnly = true)
    public List<Double> getFeedbacksByUsername2ForHolidays(String username) {
        UserSimple userSimple = userSimpleRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return feedbackRepository.filterByUserHolidays(userSimple.getUserName());

    }

    @Transactional
    public void deleteFeedbackByPost(Long id) {
        feedbackRepository.deleteRatingByPost(id);
    }

    @Transactional(readOnly = true)
    public void setFeedback(Long id) {
       List<String> users = userSimpleRepository.getAllUsers();
       for(int i=0;i<users.size();i++)
       {
           Feedback f = new Feedback();
           f.setRating(-1);
           f.setUsername(users.get(i));
           f.setPost_id(id);
           f.setHoliday_id(-1L);
           feedbackRepository.save(f);
       }

    }

    @Transactional(readOnly = false)
    public void setFeedbackForNewUser(String username) {
        List<Long> posts = postRepository.getAllIdPosts();
        for(int i=0;i<posts.size();i++)
        {
            Feedback f = new Feedback();
            f.setRating(-1);
            f.setUsername(username);
            f.setPost_id(posts.get(i));
            f.setHoliday_id(-1L);
            feedbackRepository.save(f);
        }
        List<Long> holidays = holidayRepository.getAllIdHolidays();
        for(int i=0;i<holidays.size();i++)
        {
            Feedback f = new Feedback();
            f.setRating(-1);
            f.setUsername(username);
            f.setPost_id(-1L);
            f.setHoliday_id(holidays.get(i));
            feedbackRepository.save(f);
        }

    }


    @Transactional(readOnly = true)
    public void setFeedback_Holiday(Long id) {
        List<String> users = userSimpleRepository.getAllUsers();
        for(int i=0;i<users.size();i++)
        {
            Feedback f = new Feedback();
            f.setRating(-1);
            f.setUsername(users.get(i));
            f.setPost_id(-1L);
            f.setHoliday_id(id);
            feedbackRepository.save(f);
        }

    }
    @Transactional(readOnly = true)
    public List<FeedbackDTO> getFeedbacksByHoliday(Long holidayId) {
        List<Feedback> feedbackList = feedbackRepository.filterByHoliday(holidayId);
        return feedbackList.stream().map(this::mapFromFeedbackToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<FeedbackDTO> getFeedbacksByPost(Long postId) {
        List<Feedback> feedbackList = feedbackRepository.filterByPost(postId);
        return feedbackList.stream().map(this::mapFromFeedbackToDto).collect(toList());
    }

    private FeedbackDTO mapFromFeedbackToDto(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();

        feedbackDTO.setFeedback_id(feedback.getFeedback_id());
        feedbackDTO.setUsername(feedback.getUsername());
        feedbackDTO.setPost_id(feedback.getPost_id());
        feedbackDTO.setRating(feedback.getRating());
        return feedbackDTO;
    }



    private  Feedback mapFromDtoToFeedback( FeedbackDTO feedbackDTO) {
        Feedback  feedback = new  Feedback();
        feedback.setFeedback_id(feedbackDTO.getFeedback_id());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        feedback.setUsername(loggedInUser.getUsername());
        feedback.setPost_id(feedbackDTO.getPost_id());
        feedback.setHoliday_id(-1L);
        feedback.setRating(feedbackDTO.getRating());
        return feedback;
    }

    private  Feedback mapFromDtoToFeedbackForHoliday( FeedbackDTO feedbackDTO) {
        Feedback  feedback = new  Feedback();
        feedback.setFeedback_id(feedbackDTO.getFeedback_id());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        feedback.setUsername(loggedInUser.getUsername());
        feedback.setPost_id(-1L);
        feedback.setHoliday_id(feedbackDTO.getHoliday_id());
        feedback.setRating(feedbackDTO.getRating());
        return feedback;
    }

}
