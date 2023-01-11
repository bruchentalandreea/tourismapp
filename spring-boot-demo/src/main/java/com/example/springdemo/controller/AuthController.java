package com.example.springdemo.controller;

import com.example.springdemo.dto.UserLoginDTO;
import com.example.springdemo.dto.UserViewDTO;
import com.example.springdemo.dto.WalletDTO;
import com.example.springdemo.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;
    private final PasswordService passwordService;
    private final UserService userService;
    private final FeedbackService feedbackService;
    private final WalletService walletService;
    private UserService userS;

    @Autowired
    public AuthController(AuthService authService, PasswordService passwordService, UserService userService, FeedbackService feedbackService, WalletService walletService) {
        this.authService = authService;
        this.passwordService = passwordService;
        this.userService = userService;
        this.feedbackService = feedbackService;
        this.walletService = walletService;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserViewDTO userViewDTO) {
        authService.signup(userViewDTO);
        if(!userViewDTO.getUsername().equals("admin")) {
            feedbackService.setFeedbackForNewUser(userViewDTO.getUsername());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserLoginDTO userLoginDTO) {
        return authService.login(userLoginDTO);
    }

    @PostMapping("/email")
    public ResponseEntity sendEmail(@RequestBody String email) {
        passwordService.sendEmail(email);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/role/{username}")
    public ResponseEntity<String> getRole(@PathVariable("username")  @RequestBody String username) {
        return new ResponseEntity<String>( authService.getRoleByUser(username), HttpStatus.OK);
    }


    @PostMapping("/profile/{username}")
    public ResponseEntity updateProfile(@RequestBody UserViewDTO userViewDTO, @PathVariable("username")  @RequestBody String username) {
        System.out.println("USErnAME" + username);
        authService.update(userViewDTO,username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/phone/{phone_number}/{username}")
    public ResponseEntity updatePhone(@PathVariable("phone_number")  @RequestBody String phone_number, @PathVariable("username")  @RequestBody String username) {
        authService.updatePhone1(phone_number,username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/name/{name}/{username}")
    public ResponseEntity updateName(@PathVariable("name")  @RequestBody String name, @PathVariable("username")  @RequestBody String username) {
        authService.updateName(name,username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/surname/{surname}/{username}")
    public ResponseEntity updateSurname(@PathVariable("surname")  @RequestBody String surname, @PathVariable("username")  @RequestBody String username) {
        authService.updateSurname(surname,username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/address/{country}/{state}/{city}/{username}")
    public ResponseEntity updateAddress(@PathVariable("country")  @RequestBody String country,@PathVariable("state")  @RequestBody String state,@PathVariable("city")  @RequestBody String city, @PathVariable("username")  @RequestBody String username) {
        authService.updateAddress(country,state,city,username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get-user/{username}")
    public ResponseEntity<UserViewDTO> getSingleUser(@PathVariable("username")  @RequestBody String username) {
        return new ResponseEntity<>( authService.readSingleUser(username), HttpStatus.OK);
    }
}