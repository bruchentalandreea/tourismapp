package com.example.springdemo.controller;

import com.example.springdemo.dto.HolidayDTO;
import com.example.springdemo.dto.PostDTO;
import com.example.springdemo.services.HolidayService;
import com.example.springdemo.services.PostService;
import com.example.springdemo.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;


@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

    private final HolidayService holidayService;
    private final PostService postService;
    private final RecommendationService recommendationService;
    @Autowired
    HolidayController(HolidayService holidayService, PostService postService, RecommendationService recommendationService)
    {
        this.holidayService = holidayService;
        this.postService = postService;
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public ResponseEntity createHoliday(@RequestBody HolidayDTO holidayDTO) {

        holidayService.createHoliday(holidayDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HolidayDTO>> showAllHolidays() {
        return new ResponseEntity<>(holidayService.showAllHolidays(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HolidayDTO> getSingleHoliday(@PathVariable("id") @RequestBody Long id) {
        return new ResponseEntity<>(holidayService.readSingleHoliday(id), HttpStatus.OK);
    }

    @GetMapping("/get/avg/{id}")
    public ResponseEntity<Double> getAvg(@PathVariable("id") @RequestBody Long id) {
        return new ResponseEntity<Double>(holidayService.getAvgRating(id), HttpStatus.OK);
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<List<String>> getListPhoto(@PathVariable("id") @RequestBody Long id) {
        return new ResponseEntity<List<String>>(holidayService.getListPhoto(id), HttpStatus.OK);
    }

    @GetMapping("/get/price/{id}")
    public ResponseEntity<Double> getPrice(@PathVariable("id") @RequestBody Long id) {
        System.out.println("PRICE" + holidayService.getPrice(id));
        System.out.println("ID PRICE"+ id);
        return new ResponseEntity<Double>(holidayService.getPrice(id), HttpStatus.OK);
    }

    @GetMapping("/get/photo/{id}")
    public ResponseEntity<String> getPhotoId(@PathVariable("id") @RequestBody Long id) {
        return new ResponseEntity<String>(holidayService.getPhotoId(id), HttpStatus.OK);
    }

    @GetMapping("/size/{name}")
    public ResponseEntity<List<Integer>>getNrOfHolidays(@PathVariable("name") @RequestBody String name) {
        List<Integer> aux = new ArrayList<>();
        aux.add(holidayService.showAllHolidays().size());
        aux.add(postService.showAllPosts().size());
        aux.add(recommendationService.getRecommendationsForIdUser(name,3).size());
        aux.add(recommendationService.getRecommendedHolidaysForIdUser(name,3).size());
        return new ResponseEntity<List<Integer>>(aux, HttpStatus.OK);
    }


    @GetMapping("/all/by-price/{priceMax}/{priceMin}")
    public ResponseEntity<List<HolidayDTO>> filterHolidaysByPrice(@PathVariable Double priceMax, @PathVariable Double priceMin) {
        return status(HttpStatus.OK).body(holidayService.filterHolidaysByPrice(priceMax, priceMin));
    }


    @GetMapping("/all/by-location/{country}/{state}/{city}")
    public ResponseEntity<List<HolidayDTO>> filterHolidaysByLocation(@PathVariable String country, @PathVariable String state, @PathVariable String city) {
        return status(HttpStatus.OK).body(holidayService.filterHolidaysByLocation(country, state, city));
    }

    @GetMapping("/all/by-type/{type_of_vacation}")
    public ResponseEntity<List<HolidayDTO>> filterHolidaysByType(@PathVariable String type_of_vacation) {
        List<HolidayDTO> list = holidayService.filterHolidaysByType(type_of_vacation);
        return status(HttpStatus.OK).body(list);
    }

    @GetMapping("/all/by-persons/{nr_of_persons}")
    public ResponseEntity<List<HolidayDTO>> filterHolidaysByNrOfPersons(@PathVariable Integer nr_of_persons) {
        List<HolidayDTO> list = holidayService.filterHolidaysByNrOfPersons(nr_of_persons);
        return status(HttpStatus.OK).body(list);
    }

    @GetMapping("/size/by-country/{country}")
    public ResponseEntity<List<Integer>>getHolidaysByCountry(@PathVariable("country") @RequestBody String country) {
        List<Integer> aux = new ArrayList<>();
        aux.add(holidayService.getHolidaysByCountry(country).size());
        aux.add(holidayService.showAllHolidays().size());
        return new ResponseEntity<List<Integer>>(aux, HttpStatus.OK);
    }


    @GetMapping("/recommendation/{username}")
    public ResponseEntity<List<HolidayDTO>> getRecommendations(@PathVariable("username") @RequestBody String username) {
        List<Long> holidaysId = recommendationService.getRecommendedHolidaysForIdUser(username, 2);
        List<HolidayDTO> listRecommendations = new ArrayList<>();;
        Long aux = null;
        for (int i = 0; i < +holidaysId.size(); i++) {
            System.out.println(holidaysId.get(i));
            aux =holidaysId.get(i);
            listRecommendations.add(holidayService.getHolidayById(aux)) ;
            System.out.println("AUX" + aux);
        }
        return new ResponseEntity<List<HolidayDTO>>(  listRecommendations, HttpStatus.OK);
    }
}
