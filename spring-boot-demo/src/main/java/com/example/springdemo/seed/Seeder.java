package com.example.springdemo.seed;

import com.example.springdemo.entities.*;
import com.example.springdemo.repositories.*;
import com.example.springdemo.services.AuthService;
import com.example.springdemo.services.FeedbackService;
import com.example.springdemo.services.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;



@Component
//@RequiredArgsConstructor
// The Order ensures that this command line runner is ran first (before the ConsoleController)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Seeder implements CommandLineRunner {

    private final PostService postService;
    private final AuthService authService;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    private final FeedbackService feedbackService;
    private final HolidayRepository holidayRepository;
    private final FeedbackRepository feedbackRepository;
    private final CategoryRepository categoryRepository;

    public Seeder(PostService postService, AuthService authService, ImageRepository imageRepository, PostRepository postRepository, FeedbackService feedbackService, HolidayRepository holidayRepository, FeedbackRepository feedbackRepository, CategoryRepository categoryRepository) {
        this.postService = postService;
        this.authService = authService;
        this.imageRepository = imageRepository;
        this.postRepository = postRepository;
        this.feedbackService = feedbackService;
        this.holidayRepository = holidayRepository;
        this.feedbackRepository = feedbackRepository;
        this.categoryRepository = categoryRepository;
    }

    //@Override
    @Transactional
    public void run(String... args) throws Exception {
    //seedPost();

    }


    private void seedPost (){
        /////////////////////////////IMAGE////////////////////////////
        Image image = new Image();
        image.setLink("https://mdbootstrap.com/img/new/slides/148.jpg");
        image.setType("a");
        imageRepository.save(image);

        Image image1 = new Image();
        image1.setLink("https://mdbootstrap.com/img/new/slides/138.jpg");
        image1.setType("b");
        imageRepository.save(image1);

        Image image2 = new Image();
        image2.setLink("https://mdbootstrap.com/img/new/slides/128.jpg");
        image2.setType("c");
        imageRepository.save(image2);

        Image image3 = new Image();
        image3.setLink("https://mdbootstrap.com/img/new/slides/118.jpg");
        image3.setType("d");
        imageRepository.save(image3);

        Image image4 = new Image();
        image4.setLink("https://mdbootstrap.com/img/new/slides/133.jpg");
        image4.setType("d");
        imageRepository.save(image4);

        Image image5 = new Image();
        image5.setLink("https://mdbootstrap.com/img/new/slides/144.jpg");
        image5.setType("d");
        imageRepository.save(image5);

        Image image6 = new Image();
        image6.setLink("https://mdbootstrap.com/img/new/slides/154.jpg");
        image6.setType("e");
        imageRepository.save(image6);


       ///////////////////////////////POST////////////////////////////
        Post post = new Post();
        post.setTitle("Honeymoon in Tenerife");
        post.setContent("Spend your time with your lover in our guest house");
        post.setUsername("admin");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        post.setCreatedOn(dtf.format(now));
        post.setUpdatedOn(dtf.format(now));
        post.setPrice(300.0);
        post.setCheck_in_date(LocalDate.parse("2019-08-02"));
        post.setCheck_out_date(LocalDate.parse("2019-08-10"));
        post.setCity("Tenerife");
        post.setCountry("Spain");
        post.setState("Santa Cruz de Tenerife");
        Random rand = new Random();
        post.setPhotoId("1");
        post.setAvgRating(5.0);
        post.setListPhotoId("1,2,3");
        post.setNr_of_persons(2);
        post.setType_of_vacation("Honeymoon");
        postRepository.save(post);

        Feedback feedback = new Feedback();
        feedback.setUsername("admin");
        feedback.setRating(-1);
        feedback.setPost_id(1L);
        feedback.setHoliday_id(-1L);
        feedbackRepository.save(feedback);

        Post post1 = new Post();
        post1.setTitle("House in Maldive");
        post1.setContent("We have the best and unique houses in the world ");
        post1.setUsername("admin");

        post1.setCreatedOn(dtf.format(now));
        post1.setUpdatedOn(dtf.format(now));
        post1.setPrice(300.0);
        post1.setCheck_in_date(LocalDate.parse("2019-06-02"));
        post1.setCheck_out_date(LocalDate.parse("2019-06-10"));
        post1.setCity("Maldive Islands");
        post1.setCountry("Republic Maldive");
        post1.setState(" Ibrahim Mohamed Solih");

        post1.setPhotoId("2");
        post1.setAvgRating(5.0);
        post1.setListPhotoId("1,2,3");
        post1.setNr_of_persons(2);
        post1.setType_of_vacation("Summer vacation");
        postRepository.save(post1);

        Feedback feedback1 = new Feedback();
        feedback1.setUsername("admin");
        feedback1.setRating(-1);
        feedback1.setPost_id(2L);
        feedback1.setHoliday_id(-1L);
        feedbackRepository.save(feedback1);

        Post post2 = new Post();
        post2.setTitle("Apartment in Lapland");
        post2.setContent("Visit Lapland in winter to finally meet Santa Claus");
        post2.setUsername("admin");

        post2.setCreatedOn(dtf.format(now));
        post2.setUpdatedOn(dtf.format(now));
        post2.setPrice(330.0);
        post2.setCheck_in_date(LocalDate.parse("2019-12-24"));
        post2.setCheck_out_date(LocalDate.parse("2019-12-30"));
        post2.setCity("Rovaniemi");
        post2.setCountry("Finland");
        post2.setState("Lapland");

        post2.setPhotoId("3");
        post2.setAvgRating(5.0);
        post2.setListPhotoId("4,2,3");
        post2.setNr_of_persons(2);
        post2.setType_of_vacation("Winter vacation");
        postRepository.save(post2);

        Feedback feedback2 = new Feedback();
        feedback2.setUsername("admin");
        feedback2.setRating(-1);
        feedback2.setPost_id(3L);
        feedback2.setHoliday_id(-1L);
        feedbackRepository.save(feedback2);

        ///////////////////////////////HOLIDAY///////////////////////////
        Holiday holiday = new Holiday();
        holiday.setTitle("Cozy Scandinavian Cabin ");
        holiday.setContent("Very warm and cozy 5 bedroom cabin on a mountain river in the middle of wild nature. The cabin it's situated in a natural reservation at the foot of the Fagaras mountains, being 1,5 hours away both from Sibiu International Airport and Brasov city and 30 minutes away from Fagaras city. This place is perfect for those who love nature and untamed places.");
        holiday.setUsername("admin");

        holiday.setCreatedOn(dtf.format(now));
        holiday.setUpdatedOn(dtf.format(now));
        holiday.setPrice(500.0);
        holiday.setCheck_in_date(LocalDate.parse("2019-12-24"));
        holiday.setCheck_out_date(LocalDate.parse("2019-12-30"));
        holiday.setCity("Breaza");
        holiday.setCountry("Romania");
        holiday.setState("Brasov");

        holiday.setPhotoId("4");
        holiday.setAvgRating(5.0);
        holiday.setListPhotoId("1,2,3");
        holiday.setNr_of_persons(10);
        holiday.setType_of_vacation("Winter vacation");
        holidayRepository.save(holiday);

        Feedback feedback3 = new Feedback();
        feedback3.setUsername("admin");
        feedback3.setRating(-1);
        feedback3.setPost_id(-1L);
        feedback3.setHoliday_id(1L);
        feedbackRepository.save(feedback3);

        Holiday holiday1 = new Holiday();
        holiday1.setTitle("Sailing Holidays to Greek islands on Owners' Yacht ");
        holiday1.setContent("Οur private yacht with owner as Captain is available to enjoy cruising the Greek islands & coasts sailing from Athens, in 2021 season");
        holiday1.setUsername("admin");

        holiday1.setCreatedOn(dtf.format(now));
        holiday1.setUpdatedOn(dtf.format(now));
        holiday1.setPrice(299.0);
        holiday1.setCheck_in_date(LocalDate.parse("2019-12-24"));
        holiday1.setCheck_out_date(LocalDate.parse("2019-12-30"));
        holiday1.setCity("Attica");
        holiday1.setCountry("Greece");
        holiday1.setState("Athens");

        holiday1.setPhotoId("5");
        holiday1.setAvgRating(5.0);
        holiday1.setListPhotoId("5,2,3");
        holiday1.setNr_of_persons(5);
        holiday1.setType_of_vacation("Summer vacation");
        holidayRepository.save(holiday1);

        Feedback feedback4 = new Feedback();
        feedback4.setUsername("admin");
        feedback4.setRating(-1);
        feedback4.setPost_id(-1L);
        feedback4.setHoliday_id(2L);
        feedbackRepository.save(feedback4);

        Holiday holiday2 = new Holiday();
        holiday2.setTitle("Fully equipped Studio first line to the sea & Pool");
        holiday2.setContent("Comfortable studio apartment first line to the sea with infinity pool. All inclusive ! Kitchen , Wifi, Free parking on premises,Pool, Pets allowed, TV, Window AC unit, Restaurant");
        holiday2.setUsername("admin");

        holiday2.setCreatedOn(dtf.format(now));
        holiday2.setUpdatedOn(dtf.format(now));
        holiday2.setPrice(150.0);
        holiday2.setCheck_in_date(LocalDate.parse("2019-12-24"));
        holiday2.setCheck_out_date(LocalDate.parse("2019-12-30"));
        holiday2.setCity("Varna");
        holiday2.setCountry("Bulgaria");
        holiday2.setState("Byala");

        holiday2.setPhotoId("6");
        holiday2.setAvgRating(5.0);
        holiday2.setListPhotoId("6,3,2");
        holiday2.setNr_of_persons(5);
        holiday2.setType_of_vacation("Honeymoon");
        holidayRepository.save(holiday2);

        Feedback feedback5 = new Feedback();
        feedback5.setUsername("admin");
        feedback5.setRating(-1);
        feedback5.setPost_id(-1L);
        feedback5.setHoliday_id(3L);
        feedbackRepository.save(feedback5);

        //CATEGORY
        Category category = new Category();
        category.setCategory("Food");
        categoryRepository.save(category);

        Category category1 = new Category();
        category1.setCategory("Transport");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setCategory("Accommodation");
        categoryRepository.save(category2);
    }

}
