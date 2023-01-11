package com.example.springdemo.services;

import com.example.springdemo.dto.HolidayDTO;
import com.example.springdemo.dto.PostDTO;
import com.example.springdemo.entities.Holiday;
import com.example.springdemo.entities.Post;
import com.example.springdemo.errorhandler.HolidayNotFoundException;
import com.example.springdemo.repositories.FeedbackRepository;
import com.example.springdemo.repositories.ImageRepository;
import com.example.springdemo.repositories.HolidayRepository;
import com.example.springdemo.repositories.UserSimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@Service
public class HolidayService {

    private final AuthService authService;
    private final UserSimpleRepository userSimpleRepository;
    private final ImageRepository imageRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackService feedbackService;
    private HolidayDTO holidayDTO;
    private final HolidayRepository holidayRepository;

    @Autowired
    public HolidayService(AuthService authService,  UserSimpleRepository userSimpleRepository, ImageRepository imageRepository, FeedbackRepository feedbackRepository, FeedbackService feedbackService, HolidayRepository holidayRepository) {
        this.authService = authService;
        this.userSimpleRepository = userSimpleRepository;
        this.imageRepository = imageRepository;
        this.feedbackRepository = feedbackRepository;
        this.feedbackService = feedbackService;
        this.holidayRepository = holidayRepository;
    }

    @Transactional
    public List<HolidayDTO> showAllHolidays() {
        List<Holiday> holidays = holidayRepository.findAll();
        return holidays.stream().map(this::mapFromHolidayToDto).collect(toList());
    }

    @Transactional
    public void createHoliday(HolidayDTO holidayDTO) {
        Holiday holiday = mapFromDtoToHoliday(holidayDTO);
        feedbackService.setFeedback_Holiday(holiday.getId());
       holidayRepository.save(holiday);
    }

    @Transactional
    public HolidayDTO readSingleHoliday(Long id) {
        Holiday holiday = holidayRepository.findById(id).orElseThrow(() -> new HolidayNotFoundException("For id " + id));
        return mapFromHolidayToDto(holiday);
    }

    @Transactional(readOnly = true)
    public List<String> getListPhoto(Long id) {
        String idPhoto = holidayRepository.getListPhoto(id);
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

    @Transactional
    public String getPhotoId(Long id) {
        String photoId= holidayRepository.getPhotoId(id);
        return photoId;
    }


    @Transactional(readOnly = true)
    public List<HolidayDTO> filterHolidaysByPrice(Double priceMax, Double priceMin) {
        List<Holiday> holidays = holidayRepository.filterByPrice(priceMax,priceMin);
        return holidays.stream().map(this::mapFromHolidayToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<HolidayDTO> filterHolidaysByLocation(String country, String state, String city) {
        List<Holiday> holidays = holidayRepository.filterByLocation(country, state, city);
        return holidays.stream().map(this::mapFromHolidayToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<HolidayDTO> filterHolidaysByType(String type_of_vacation) {
        List<Holiday> holidays = holidayRepository.filterByType(type_of_vacation);
        return holidays.stream().map(this::mapFromHolidayToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<HolidayDTO> filterHolidaysByNrOfPersons(Integer nr_of_persons) {
        List<Holiday> holidays = holidayRepository.filterByNrOfPersons(nr_of_persons);
        return holidays.stream().map(this::mapFromHolidayToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<HolidayDTO> getHolidaysByCountry(String country) {
        List<Holiday> holidays = holidayRepository.getHolidaysByCountry(country);
        return holidays.stream().map(this::mapFromHolidayToDto).collect(toList());
    }

    @Transactional
    public HolidayDTO getHolidayById(Long id) {
        Holiday holiday  = holidayRepository.getHolidayById(id);
        return mapFromHolidayToDto(holiday);
    }


    @Transactional(readOnly = true)
    public List<Long> getIdHolidaysReviewedByUser(String username) {
        List<Long> id = holidayRepository.getIdHolidaysReviewedByUser(username);
        return id;
    }

    @Transactional(readOnly = true)
    public List<Long> getAllIdHolidays() {
        List<Long> id = holidayRepository.getAllIdHolidays();
        return id;
    }

    @Transactional(readOnly = true)
    public List<Long>  getIdHolidaysUnReviewedByUser(String username) {
        List<Long> reviewed = getIdHolidaysReviewedByUser(username);
        List<Long>  all = getAllIdHolidays();

        List toReturn = new ArrayList(all);
        toReturn.removeAll(reviewed);

        return toReturn;
    }

    private HolidayDTO mapFromHolidayToDto(Holiday holiday) {
        HolidayDTO holidayDTO = new HolidayDTO();
        holidayDTO.setId(holiday.getId());
        holidayDTO.setTitle(holiday.getTitle());
        holidayDTO.setContent(holiday.getContent());
        holidayDTO.setUsername(holiday.getUsername());
        holidayDTO.setPrice(holiday.getPrice());
        holidayDTO.setCheck_in_date(holiday.getCheck_in_date());
        holidayDTO.setCheck_out_date(holiday.getCheck_out_date());
        holidayDTO.setCreatedOn(holiday.getCreatedOn());
        holidayDTO.setUpdatedOn(holiday.getUpdatedOn());
        holidayDTO.setCity(holiday.getCity());
        holidayDTO.setCountry(holiday.getCountry());
        holidayDTO.setState(holiday.getState());
        String aux = imageRepository.findImage(holiday.getId());
        String aux1 = imageRepository.findImageByPhotoId(holiday.getPhotoId());
        holidayDTO.setPhotoId(aux1);

        holidayDTO.setAvgRating(holiday.getAvgRating());
        holidayDTO.setListPhotoId(holiday.getListPhotoId());
        holidayDTO.setNr_of_persons(holiday.getNr_of_persons());
        holidayDTO.setType_of_vacation(holiday.getType_of_vacation());
        return holidayDTO;
    }

    private Holiday mapFromDtoToHoliday(HolidayDTO holidayDTO) {
        Holiday holiday = new Holiday();
        holiday.setId(holidayDTO.getId());
        holiday.setTitle(holidayDTO.getTitle());
        holiday.setContent(holidayDTO.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        holiday.setCreatedOn(dtf.format(now));
        holiday.setUsername(loggedInUser.getUsername());
        holiday.setUpdatedOn(dtf.format(now));

        holiday.setPrice(holidayDTO.getPrice());
        holiday.setCheck_in_date(holidayDTO.getCheck_in_date());
        holiday.setCheck_out_date(holidayDTO.getCheck_out_date());

        holiday.setCity(holidayDTO.getCity());
        holiday.setCountry(holidayDTO.getCountry());
        holiday.setState(holidayDTO.getState());

        Random rand = new Random();
        String randomElement = imageRepository.getAllIds().get(rand.nextInt(imageRepository.getAllIds().size()));
        holiday.setPhotoId(randomElement);
        holiday.setAvgRating(5.0);
        holiday.setListPhotoId(holidayDTO.getListPhotoId());
        holiday.setNr_of_persons(holidayDTO.getNr_of_persons());
        holiday.setType_of_vacation(holidayDTO.getType_of_vacation());
        return holiday;
    }


    @Transactional(readOnly = true)
    public Double getPrice(Long id) {
        Double price = holidayRepository.getPriceByID(id);
        return price;
    }

    @Transactional
    public void updateHolidayPrice(Double value, Long id) {
        holidayRepository.updatePrice(value, id);
    }

    @Transactional
    public void updateHoliday(Double value, Long id) {
        holidayRepository.updateRating(value, id);
    }

    @Transactional(readOnly = true)
    public Double getAvgRating(Long id) {
        Double current_avg = holidayRepository.getRatingbyID(id);
        return current_avg;
    }


}