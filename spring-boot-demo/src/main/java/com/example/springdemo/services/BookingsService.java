package com.example.springdemo.services;

import com.example.springdemo.dto.BookingsDTO;
import com.example.springdemo.dto.PostDTO;
import com.example.springdemo.entities.Bookings;
import com.example.springdemo.entities.Post;
import com.example.springdemo.entities.UserSimple;
import com.example.springdemo.errorhandler.PostNotFoundException;
import com.example.springdemo.repositories.BookingsRepository;
import com.example.springdemo.repositories.FeedbackRepository;
import com.example.springdemo.repositories.ImageRepository;
import com.example.springdemo.repositories.UserSimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@Service
public class BookingsService {

    private final AuthService authService;
    private final UserSimpleRepository userSimpleRepository;
    private final ImageRepository imageRepository;
    private final BookingsRepository bookingsRepository;

    @Autowired
    public BookingsService(AuthService authService, UserSimpleRepository userSimpleRepository, ImageRepository imageRepository,BookingsRepository bookingsRepository) {
        this.authService = authService;
        this.userSimpleRepository = userSimpleRepository;
        this.imageRepository = imageRepository;
        this.bookingsRepository = bookingsRepository;
    }

    @Transactional
    public void deleteBookingsById(Long id) {
        bookingsRepository.deleteBookings(id);
    }

    @Transactional
    public void createBookings(BookingsDTO postDTO) {
        Bookings bookings = mapFromDtoToBookings(postDTO);
        bookingsRepository.save(bookings);
    }

    @Transactional
    public BookingsDTO readSingleBooking(Long id) {
        Bookings bookings = bookingsRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromBookingsToDto(bookings);
    }

    @Transactional(readOnly = true)
    public Double getAvgRating(Long id) {
        Double current_avg = bookingsRepository.getRatingbyID(id);
        return current_avg;
    }

    @Transactional(readOnly = true)
    public List<BookingsDTO> getBookingsByUsernamePost(String username) {
        UserSimple userSimple = userSimpleRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return bookingsRepository.findByUserSimplePost(userSimple.getUserName()).stream().map(this::mapFromBookingsToDto).collect(toList());

    }

    @Transactional(readOnly = true)
    public List<BookingsDTO> getBookingsByUsernameHoliday(String username) {
        UserSimple userSimple = userSimpleRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return bookingsRepository.findByUserSimpleHoliday(userSimple.getUserName()).stream().map(this::mapFromBookingsToDto).collect(toList());

    }

    @Transactional(readOnly = true)
    public BookingsDTO getLast() {
        Bookings bookings = bookingsRepository.findLastBooking();
        return  mapFromBookingsToDto(bookings);

    }

    private BookingsDTO mapFromBookingsToDto(Bookings bookings) {
        BookingsDTO bookingsDTO = new BookingsDTO();
        bookingsDTO.setId(bookings.getId());
        bookingsDTO.setTitle(bookings.getTitle());
        bookingsDTO.setPrice(bookings.getPrice());
        bookingsDTO.setCheck_in_date(bookings.getCheck_in_date());
        bookingsDTO.setCheck_out_date(bookings.getCheck_out_date());
        bookingsDTO.setBookedBy(bookings.getBookedBy());
        bookingsDTO.setCity(bookings.getCity());
        bookingsDTO.setCountry(bookings.getCountry());
        bookingsDTO.setState(bookings.getState());
        String aux = imageRepository.findImage(bookings.getId());
        String aux1 = imageRepository.findImageByPhotoId(bookings.getPhotoId());
        bookingsDTO.setPhotoId(aux1);
        bookingsDTO.setAvgRating(bookings.getAvgRating());
        bookingsDTO.setNr_of_persons(bookings.getNr_of_persons());
        bookingsDTO.setType_of_vacation(bookings.getType_of_vacation());
        bookingsDTO.setHoliday_id(bookings.getHoliday_id());
        bookingsDTO.setPost_id(bookings.getPost_id());
        return bookingsDTO;
    }

    private Bookings mapFromDtoToBookings(BookingsDTO bookingsDTO) {
        Bookings bookings = new Bookings();
        bookings.setId(bookingsDTO.getId());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        bookings.setPrice(bookingsDTO.getPrice());
        bookings.setCheck_in_date(bookingsDTO.getCheck_in_date());
        bookings.setCheck_out_date(bookingsDTO.getCheck_out_date());
        bookings.setBookedBy(loggedInUser.getUsername());
        bookings.setTitle(bookingsDTO.getTitle());
        bookings.setCity(bookingsDTO.getCity());
        bookings.setCountry(bookingsDTO.getCountry());
        bookings.setState(bookingsDTO.getState());
        bookings.setPhotoId(bookingsDTO.getPhotoId());
        bookings.setAvgRating(5.0);
        bookings.setNr_of_persons(bookingsDTO.getNr_of_persons());
        bookings.setType_of_vacation(bookingsDTO.getType_of_vacation());
        bookings.setHoliday_id(bookingsDTO.getHoliday_id());
        bookings.setPost_id(bookingsDTO.getPost_id());
        return bookings;
    }
}
