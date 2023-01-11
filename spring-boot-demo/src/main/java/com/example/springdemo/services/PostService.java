package com.example.springdemo.services;


import com.example.springdemo.dto.PostDTO;
import com.example.springdemo.entities.Post;
import com.example.springdemo.entities.UserSimple;
import com.example.springdemo.errorhandler.PostNotFoundException;
import com.example.springdemo.repositories.FeedbackRepository;
import com.example.springdemo.repositories.ImageRepository;
import com.example.springdemo.repositories.PostRepository;
import com.example.springdemo.repositories.UserSimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PostService {

    private final AuthService authService;
    private final PostRepository postRepository;
    private final UserSimpleRepository userSimpleRepository;
    private final ImageRepository imageRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackService feedbackService;

    private PostDTO postDTO;

    @Autowired
    public PostService(AuthService authService, PostRepository postRepository, UserSimpleRepository userSimpleRepository, ImageRepository imageRepository, FeedbackRepository feedbackRepository, FeedbackService feedbackService) {
        this.authService = authService;
        this.postRepository = postRepository;
        this.userSimpleRepository = userSimpleRepository;
        this.imageRepository = imageRepository;
        this.feedbackRepository = feedbackRepository;
        this.feedbackService = feedbackService;

    }

    @Transactional
    public List<PostDTO> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public PostDTO getPostById(Long id) {
        Post post = postRepository.getPostById(id);
        return mapFromPostToDto(post);
    }

    @Transactional
    public void deletePostById(Long id) {
         postRepository.deletePost(id);
    }

    @Transactional
    public void createPost(PostDTO postDTO) {
        Post post = mapFromDtoToPost(postDTO);
        postRepository.save(post);
        feedbackService.setFeedback(post.getId());
    }

    @Transactional
    public PostDTO readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostDTO> getPostsByUsername(String username) {
        UserSimple userSimple = userSimpleRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

   return postRepository.findByUserSimple(userSimple.getUserName()).stream().map(this::mapFromPostToDto).collect(toList());

    }
    @Transactional(readOnly = true)
    public Double getPrice(Long id) {
        Double price = postRepository.getPriceByID(id);
        return price;
    }

    @Transactional(readOnly = true)
    public List<PostDTO> filterPostsByPrice(Double priceMax, Double priceMin) {
        List<Post> posts = postRepository.filterByPrice(priceMax,priceMin);
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostDTO> filterPostsByDate(LocalDate check_in_date, LocalDate check_out_date) {
        List<Post> posts = postRepository.filterByDate(check_in_date,check_out_date);
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostDTO> filterPostsByLocation(String country, String state, String city) {
        List<Post> posts = postRepository.filterByLocation(country, state, city);
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostDTO> filterPostsByType(String type_of_vacation) {
        List<Post> posts = postRepository.filterByType(type_of_vacation);
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostDTO> filterPostsByNrOfPersons(Integer nr_of_persons) {
        List<Post> posts = postRepository.filterByNrOfPersons(nr_of_persons);
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<Long> getIdPostsReviewedByUser(String username) {
        List<Long> id = postRepository.getIdPostsReviewedByUser(username);
        return id;
    }

    @Transactional(readOnly = true)
    public List<Long> getAllIdPosts() {
        List<Long> id = postRepository.getAllIdPosts();
        return id;
    }

    @Transactional(readOnly = true)
    public List<Long>  getIdPostsUnReviewedByUser(String username) {
        List<Long> reviewed = getIdPostsReviewedByUser(username);
        List<Long>  all = getAllIdPosts();

        List toReturn = new ArrayList(all);
        toReturn.removeAll(reviewed);

        return toReturn;
    }

    @Transactional(readOnly = true)
    public List<String> getListPhoto(Long id) {
        String idPhoto = postRepository.getListPhoto(id);
        List<String> links = new ArrayList<>();
        List<String> elephantList = Arrays.asList(idPhoto.split(","));
        System.out.println(elephantList);
        for (int i = 0; i < elephantList.size(); i++) {
            System.out.println(elephantList.get(i));
            String aux = imageRepository.findImage(Long.parseLong(elephantList.get(i)));

             links.add(aux);

        }
        return links;
    }

    @Transactional(readOnly = true)
    public List<PostDTO> search(Integer nr_of_persons, String city,String type_of_vacation) {
        List<Post> posts = postRepository.search(nr_of_persons, city,type_of_vacation);
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public String getPhotoId(Long id) {
        String photoId= postRepository.getPhotoId(id);
        return photoId;
    }
    private PostDTO mapFromPostToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setUsername(post.getUsername());
        postDTO.setPrice(post.getPrice());
        postDTO.setCreatedOn(post.getCreatedOn());
        postDTO.setUpdatedOn(post.getUpdatedOn());
        postDTO.setCheck_in_date(post.getCheck_in_date());
        postDTO.setCheck_out_date(post.getCheck_out_date());

        postDTO.setCity(post.getCity());
        postDTO.setCountry(post.getCountry());
        postDTO.setState(post.getState());
        String aux = imageRepository.findImage(post.getId());
        String aux1 = imageRepository.findImageByPhotoId(post.getPhotoId());
        postDTO.setPhotoId(aux1);
        postDTO.setAvgRating(post.getAvgRating());
        postDTO.setListPhotoId(post.getListPhotoId());
        postDTO.setNr_of_persons(post.getNr_of_persons());
        postDTO.setType_of_vacation(post.getType_of_vacation());
        return postDTO;
    }

    private Post mapFromDtoToPost(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setUsername(loggedInUser.getUsername());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        post.setCreatedOn(dtf.format(now));
        post.setUpdatedOn(dtf.format(now));


        // post.setCreatedOn(Instant.now());
       // post.setUpdatedOn(Instant.now());

        post.setPrice(postDTO.getPrice());
        post.setCheck_in_date(postDTO.getCheck_in_date());
        post.setCheck_out_date(postDTO.getCheck_out_date());

        post.setCity(postDTO.getCity());
        post.setCountry(postDTO.getCountry());
        post.setState(postDTO.getState());
        Random rand = new Random();
        String randomElement = imageRepository.getAllIds().get(rand.nextInt(imageRepository.getAllIds().size()));
        post.setPhotoId(randomElement);
        post.setAvgRating(5.0);

         List<String> id_images = imageRepository.getAllIds();
        Collections.shuffle(id_images);
        List<String> l = new ArrayList<String>();
        l.add(id_images.get(1));
        l.add(id_images.get(2));
        l.add(id_images.get(3));
        String result = String.join(", ", l);
        post.setListPhotoId(result.replaceAll("\\s+",""));

        post.setNr_of_persons(postDTO.getNr_of_persons());
        post.setType_of_vacation(postDTO.getType_of_vacation());
        return post;
    }

    @Transactional(readOnly = true)
    public Double getAvgRating(Long id) {
        Double current_avg = postRepository.getRatingbyID(id);
        return current_avg;
    }

    @Transactional(readOnly = true)
    public Double getAvgRatingHoliday(Long id) {
        Double current_avg = postRepository.getRatingbyID(id);
        return current_avg;
    }

    @Transactional
    public void updatePost(Double value, Long id) {
        postRepository.updateRating(value, id);
    }

}