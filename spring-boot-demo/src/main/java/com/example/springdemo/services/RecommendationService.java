package com.example.springdemo.services;


import org.springframework.stereotype.Service;
import com.example.springdemo.dto.PostDTO;
import com.example.springdemo.repositories.FeedbackRepository;
import com.example.springdemo.repositories.ImageRepository;
import com.example.springdemo.repositories.PostRepository;
import com.example.springdemo.repositories.UserSimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class RecommendationService {


    private final AuthService authService;
    private final PostRepository postRepository;
    private final FeedbackService feedbackService;
    private final UserSimpleRepository userSimpleRepository;
    private final ImageRepository imageRepository;
    private final FeedbackRepository feedbackRepository;
    private final PostService postService;
    private final HolidayService holidayService;
    private PostDTO postDTO;

    @Autowired
    public RecommendationService(AuthService authService, PostRepository postRepository, FeedbackService feedbackService, UserSimpleRepository userSimpleRepository, ImageRepository imageRepository, FeedbackRepository feedbackRepository, PostService postService, HolidayService holidayService) {
        this.authService = authService;
        this.postRepository = postRepository;
        this.feedbackService = feedbackService;
        this.userSimpleRepository = userSimpleRepository;
        this.imageRepository = imageRepository;
        this.feedbackRepository = feedbackRepository;
        this.postService = postService;
        this.holidayService = holidayService;
    }


    //returneaza postarile recomandate
    @Transactional(readOnly = true)
    public List<Long>  getRecommendationsForIdUser (String name, int k)
    {
        HashMap<String, Double> distanceModels = getDistanceUserList(name);
        List<String> kNearestUsers = getKNearestUsers(k, distanceModels);
        List<Long> recommendedPosts =  getRecommendedPostsInOrder(name,kNearestUsers);

     return  recommendedPosts;
    }

    //returneaza distantele intre un utilizator si restul

    private HashMap<String, Double> getDistanceUserList(String name)
    {

        HashMap<String, Double> distanceUserList = new HashMap<String, Double>();
        List<Double> ratingUser1 = feedbackService.getFeedbacksByUsername2(name);
        System.out.println("Rating User" + ratingUser1);
        List<String> users = userSimpleRepository.getNames(name);
        System.out.println("Users" + users);
        for(int i=0;i< users.size(); i++)
        {
            if(users.get(i) != name)
            {
                double dist = getDistanceByRatingList(ratingUser1, users.get(i));
                distanceUserList.put(users.get(i), dist);
                System.out.println("Users(i)" + users.get(i));
                System.out.println("DIST"+ dist);
            }
        }
        return distanceUserList;
    }


    //calcularea distantei euclidiene
    private double getDistanceByRatingList(List<Double> rating1,String name )
    {
        List<Double> rating2 = feedbackService.getFeedbacksByUsername2(name);
        double rez=0;

        if(rating1.size() != rating2.size())
            return rez;
        for(int i=0;i< rating1.size();i++)
        {
            rez+=Math.pow((rating1.get(i) - rating2.get(i)), 2.0);

        }
        rez = Math.sqrt(rez);
        return rez;
    }


    private List<String> getKNearestUsers (int k ,  HashMap<String, Double> distanceUserList)
    {
        List<String> keys = new ArrayList<>(distanceUserList.keySet());
        Collections.sort(keys);
        List<String> firstKElementsList = keys.stream().limit(k).collect(Collectors.toList());

        return firstKElementsList;
    }

    private List<Long> getRecommendedPostsInOrder (String username, List<String> kNearestUsers )
    {
        HashMap<Long, Double> postEstimatedRating = new HashMap<Long, Double>();
        List<Long> unreviewedPosts = postService.getIdPostsUnReviewedByUser(username);
        for(int i=0;i<unreviewedPosts.size();i++)
        {
            Double estimatedRating = 0.0;
            for(int k=0;k<kNearestUsers.size();k++)
            {
                Double rating = feedbackRepository.findRating(username, unreviewedPosts.get(i));
                if(rating!= -1)
                {
                    estimatedRating +=rating;
                }
                if(estimatedRating != 0)
                {
                    estimatedRating /=kNearestUsers.size();
                }
                postEstimatedRating.put(unreviewedPosts.get(i),estimatedRating);
            }
        }

        Map<Long,Double> result = postEstimatedRating.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return new ArrayList<>(result.keySet());
    }



    /////////////////HOLIDAYS////////////////////////


    //returneaza locurile recomandate
    @Transactional(readOnly = true)
    public List<Long>  getRecommendedHolidaysForIdUser (String name, int k)
    {
        HashMap<String, Double> distanceModels = getDistanceUserListHolidays(name);
        List<String> kNearestUsers = getKNearestUsers(k, distanceModels);
        List<Long> recommendedHolidays =  getRecommendedHolidaysInOrder(name,kNearestUsers);

        return  recommendedHolidays;
    }


    //returneaza distantele intre un utilizator si restul

    private HashMap<String, Double> getDistanceUserListHolidays(String name)
    {

        HashMap<String, Double> distanceUserList = new HashMap<String, Double>();
        List<Double> ratingUser1 = feedbackService.getFeedbacksByUsername2ForHolidays(name);
        System.out.println("Rating User" + ratingUser1);
        List<String> users = userSimpleRepository.getNames(name);
        System.out.println("Users" + users);
        for(int i=0;i< users.size(); i++)
        {
            if(users.get(i) != name)
            {
                double dist = getDistanceByRatingListHolidays(ratingUser1, users.get(i));
                distanceUserList.put(users.get(i), dist);
                System.out.println("Users(i)" + users.get(i));
                System.out.println("DIST"+ dist);
            }
        }
        return distanceUserList;
    }


    //calcularea distantei euclidiene
    private double getDistanceByRatingListHolidays(List<Double> rating1,String name )
    {
        List<Double> rating2 = feedbackService.getFeedbacksByUsername2ForHolidays(name);
        double rez=0;

        if(rating1.size() != rating2.size())
            return rez;
        for(int i=0;i< rating1.size();i++)
        {
            rez+=Math.pow((rating1.get(i) - rating2.get(i)), 2.0);

        }
        rez = Math.sqrt(rez);
        return rez;
    }

    private List<Long> getRecommendedHolidaysInOrder (String username, List<String> kNearestUsers )
    {
        HashMap<Long, Double> holidaysEstimatedRating = new HashMap<Long, Double>();
        List<Long> unreviewedHolidays = holidayService.getIdHolidaysUnReviewedByUser(username);
        for(int i=0;i<unreviewedHolidays.size();i++)
        {
            Double estimatedRating = 0.0;
            for(int k=0;k<kNearestUsers.size();k++)
            {
                Double rating = feedbackRepository.findRatingHoliday(username, unreviewedHolidays.get(i));
                if(rating!= -1)
                {
                    estimatedRating +=rating;
                }
                if(estimatedRating != 0)
                {
                    estimatedRating /=kNearestUsers.size();
                }
                holidaysEstimatedRating.put(unreviewedHolidays.get(i),estimatedRating);
            }
        }

        Map<Long,Double> result = holidaysEstimatedRating.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return new ArrayList<>(result.keySet());
    }
}
