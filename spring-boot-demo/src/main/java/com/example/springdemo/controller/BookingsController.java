package com.example.springdemo.controller;

import com.example.springdemo.dto.BookingsDTO;
import com.example.springdemo.dto.PostDTO;
import com.example.springdemo.services.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;


@RestController
@RequestMapping("/api/bookings")
public class BookingsController {

    private final BookingsService bookingsService;

    @Autowired
    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @PostMapping
    public ResponseEntity createBooking(@RequestBody BookingsDTO bookingsDTO) {
        bookingsService.createBookings(bookingsDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity< BookingsDTO> getSingleBooking(@PathVariable("id") @RequestBody Long id) {
        return new ResponseEntity<>(bookingsService.readSingleBooking(id), HttpStatus.OK);
    }

    @GetMapping("/get/avg/{id}")
    public ResponseEntity<Double> getAvg(@PathVariable("id") @RequestBody Long id) {
        return new ResponseEntity<Double>(bookingsService.getAvgRating(id), HttpStatus.OK);
    }

    @GetMapping("/by-user/post/{name}")
    public ResponseEntity<List<BookingsDTO>> getBookingsByUsernamePost(@PathVariable String name) {
        List<BookingsDTO> list = bookingsService.getBookingsByUsernamePost(name);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getTitle());
        }
        return status(HttpStatus.OK).body(bookingsService.getBookingsByUsernamePost(name));
    }

    @GetMapping("/by-user/holiday/{name}")
    public ResponseEntity<List<BookingsDTO>> getBookingsByUsernameHoliday(@PathVariable String name) {
        List<BookingsDTO> list = bookingsService.getBookingsByUsernameHoliday(name);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getTitle());
        }
        return status(HttpStatus.OK).body(bookingsService.getBookingsByUsernameHoliday(name));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        bookingsService.deleteBookingsById(id);
        //bookingsService.deleteFeedbackByBookings(id);
        System.out.println("post deleted"+ id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/last")
    public ResponseEntity<BookingsDTO> getLast() {
        System.out.println("IMAGEEE" + bookingsService.getLast().getPhotoId());
        return status(HttpStatus.OK).body(bookingsService.getLast());
    }
}
