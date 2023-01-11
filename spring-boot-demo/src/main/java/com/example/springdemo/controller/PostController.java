package com.example.springdemo.controller;

import com.example.springdemo.dto.CategoryDTO;
import com.example.springdemo.dto.PostDTO;
import com.example.springdemo.services.CategoryService;
import com.example.springdemo.services.FeedbackService;
import com.example.springdemo.services.PostService;
import com.example.springdemo.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private final PostService postService;
    private final CategoryService categoryService;
    private final RecommendationService recommendationService;
    private final FeedbackService feedbackService;

    @Autowired
    public PostController(PostService postService, CategoryService categoryService, RecommendationService recommendationService, FeedbackService feedbackService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.recommendationService = recommendationService;
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDTO postDTO) {
        postService.createPost(postDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDTO> getSinglePost(@PathVariable("id") @RequestBody Long id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }

    @GetMapping("/get/avg/{id}")
    public ResponseEntity<Double> getAvg(@PathVariable("id") @RequestBody Long id) {
        return new ResponseEntity<Double>(postService.getAvgRating(id), HttpStatus.OK);
    }


    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostDTO>> getPostsByUsername(@PathVariable String name) {
        List<PostDTO> list = postService.getPostsByUsername(name);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getTitle());
        }
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }


    @GetMapping("/all/by-price/{priceMax}/{priceMin}")
    public ResponseEntity<List<PostDTO>> filterPostsByPrice(@PathVariable Double priceMax, @PathVariable Double priceMin) {
        return status(HttpStatus.OK).body(postService.filterPostsByPrice(priceMax, priceMin));
    }

    @GetMapping("/all/by-date/{check_in_date}/{check_out_date}")
    public ResponseEntity<List<PostDTO>> filterPostsByDate(@PathVariable LocalDate check_in_date, @PathVariable LocalDate check_out_date) {
        return status(HttpStatus.OK).body(postService.filterPostsByDate(check_in_date, check_out_date));
    }


    @GetMapping("/all/by-location/{country}/{state}/{city}")
    public ResponseEntity<List<PostDTO>> filterPostsByLocation(@PathVariable String country, @PathVariable String state, @PathVariable String city) {
        return status(HttpStatus.OK).body(postService.filterPostsByLocation(country, state, city));
    }

    @GetMapping("/all/by-type/{type_of_vacation}")
    public ResponseEntity<List<PostDTO>> filterPostsByType(@PathVariable String type_of_vacation) {
        List<PostDTO> list = postService.filterPostsByType(type_of_vacation);
        return status(HttpStatus.OK).body(list);
    }

    @GetMapping("/all/by-persons/{nr_of_persons}")
    public ResponseEntity<List<PostDTO>> filterPostsByNrOfPersons(@PathVariable Integer nr_of_persons) {
        List<PostDTO> list = postService.filterPostsByNrOfPersons(nr_of_persons);
        return status(HttpStatus.OK).body(list);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> showAllCategories() {

        return new ResponseEntity<>(categoryService.showAllCategories(), HttpStatus.OK);
    }


    @GetMapping("/photo/{id}")
    public ResponseEntity<List<String>> getListPhoto(@PathVariable("id") @RequestBody Long id) {
        return new ResponseEntity<List<String>>(postService.getListPhoto(id), HttpStatus.OK);
    }

    @GetMapping("/idPostsReviewed/{username}")
    public ResponseEntity<List<Long>> getIdPostsReviewed(@PathVariable("username") @RequestBody String username) {
        return new ResponseEntity<List<Long>>(postService.getIdPostsReviewedByUser(username), HttpStatus.OK);
    }

    @GetMapping("/allIds")
    public ResponseEntity<List<Long>> getAllIdPosts() {
        return new ResponseEntity<List<Long>>(postService.getAllIdPosts(), HttpStatus.OK);
    }




    @GetMapping("/idPostsUnReviewed/{username}")
    public ResponseEntity<List<Long>> getIdPostsUnReviewed(@PathVariable("username") @RequestBody String username) {
        return new ResponseEntity<List<Long>>(postService.getIdPostsUnReviewedByUser(username), HttpStatus.OK);
    }

    @GetMapping("/recommendation/{username}")
    public ResponseEntity<List<PostDTO>> getRecommendations(@PathVariable("username") @RequestBody String username) {
        List<Long> postsId = recommendationService.getRecommendationsForIdUser(username, 2);
        List<PostDTO> listRecommendations = new ArrayList<>();;
        Long aux = null;
        for (int i = 0; i < +postsId.size(); i++) {
            System.out.println(postsId.get(i));
            aux = postsId.get(i);
           listRecommendations.add(postService.getPostById(aux)) ;
           System.out.println("AUX" + aux);
        }
        return new ResponseEntity<List<PostDTO>>(  listRecommendations, HttpStatus.OK);
    }


    @GetMapping("/search/{nr_of_persons}/{city}/{type_of_vacation}")
    public ResponseEntity<List<PostDTO>> search(@PathVariable Integer nr_of_persons, @PathVariable String city, @PathVariable String type_of_vacation) {
        List<PostDTO> list = postService.search(nr_of_persons, city, type_of_vacation);
        return status(HttpStatus.OK).body(list);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        feedbackService.deleteFeedbackByPost(id);
        System.out.println("post deleted"+ id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/photo/{id}")
    public ResponseEntity<String> getPhotoId(@PathVariable("id") @RequestBody Long id) {
        return new ResponseEntity<String>(postService.getPhotoId(id), HttpStatus.OK);
    }

    @GetMapping("/get/price/{id}")
    public ResponseEntity<Double> getPrice(@PathVariable("id") @RequestBody Long id) {
        System.out.println("PRICE" + postService.getPrice(id));
        System.out.println("ID PRICE"+ id);
        return new ResponseEntity<Double>(postService.getPrice(id), HttpStatus.OK);
    }
}