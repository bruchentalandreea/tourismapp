package com.example.springdemo.services;

import com.example.springdemo.dto.UserLoginDTO;
import com.example.springdemo.dto.UserViewDTO;
import com.example.springdemo.entities.*;
import com.example.springdemo.errorhandler.PostNotFoundException;
import com.example.springdemo.repositories.UserSimpleRepository;
import com.example.springdemo.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;


@Service
public class AuthService {

    private final UserSimpleRepository userSimpleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private Collection<? extends GrantedAuthority> authorities;


    @Autowired
    public AuthService(UserSimpleRepository userSimpleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTProvider jwtProvider) {
        this.userSimpleRepository = userSimpleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;

    }

    public void signup(UserViewDTO registerRequest) {
        /*
        List<GrantedAuthority> authorities = registerRequest.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());*/


        UserSimple userSimple = new UserSimple();
        userSimple.setUserName(registerRequest.getUsername());
        userSimple.setEmail(registerRequest.getEmail());
        userSimple.setPassword(encodePassword(registerRequest.getPassword()));
        userSimple.setUser_role(registerRequest.getUser_role());

        userSimpleRepository.save(userSimple);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public  AuthResponse login(UserLoginDTO userloginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userloginDTO.getUsername(),
                userloginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthResponse(authenticationToken, userloginDTO.getUsername());
    }

    public Optional<User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

    @Transactional(readOnly = true)
    public String getRoleByUser(String username) {
        String role = userSimpleRepository.getRole(username);
        return role;
    }





    public void update(UserViewDTO registerRequest, String username) throws UsernameNotFoundException {
        UserSimple userSimple = userSimpleRepository.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException("No user found " + username));
        userSimple.setEmail(registerRequest.getEmail());
        userSimple.setPhone_number(registerRequest.getPhone_number());
        userSimple.setName(registerRequest.getName());
        userSimple.setSurname(registerRequest.getSurname());
        userSimple.setCountry(registerRequest.getCountry());
        userSimple.setState(registerRequest.getState());
        userSimple.setCity(registerRequest.getCity());
        userSimpleRepository.save(userSimple);
    }

    public void updatePhone(UserViewDTO registerRequest, String username) throws UsernameNotFoundException {
        UserSimple userSimple = userSimpleRepository.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException("No user found " + username));
        userSimple.setPhone_number(registerRequest.getPhone_number());
    }

    @Transactional
    public void updatePhone1(String phone_number,String user_name) {
        userSimpleRepository.updatePhone(phone_number,user_name);
    }

    @Transactional
    public void updateName(String name,String user_name) {
        userSimpleRepository.updateName(name,user_name);
    }

    @Transactional
    public void updateSurname(String surname,String user_name) {
        userSimpleRepository.updateSurname(surname,user_name);
    }

    @Transactional
    public void updateAddress(String country,String state,String city ,String user_name) {
        userSimpleRepository.updateAddress(country,state,city,user_name);
    }

    @Transactional
    public UserViewDTO readSingleUser(String username) {
        UserSimple userSimple = userSimpleRepository.findByUserName(username).orElseThrow(() -> new PostNotFoundException("For username " + username));
        return mapFromUserToDto(userSimple);
    }

    private UserViewDTO mapFromUserToDto(UserSimple userSimple) {
        UserViewDTO userViewDTO =new UserViewDTO();
        userViewDTO.setUsername(userSimple.getUserName());
        userViewDTO.setPassword(userSimple.getPassword());
        userViewDTO.setId(userSimple.getId());
        userViewDTO.setName(userSimple.getName());
        userViewDTO.setSurname(userSimple.getSurname());
        userViewDTO.setEmail(userSimple.getEmail());
        userViewDTO.setCountry(userSimple.getCountry());
        userViewDTO.setState(userSimple.getState());
        userViewDTO.setCity(userSimple.getCity());
        userViewDTO.setPhone_number(userSimple.getPhone_number());
        userViewDTO.setUser_role(userSimple.getUser_role());

        return userViewDTO;
    }
}