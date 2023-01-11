package com.example.springdemo.services;

import com.example.springdemo.dto.PostDTO;
import com.example.springdemo.entities.Post;
import com.example.springdemo.entities.UserSimple;
import com.example.springdemo.repositories.UserSimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class UserService  implements UserDetailsService {

    private final UserSimpleRepository userSimpleRepository;
    private Collection<? extends GrantedAuthority> authorities;


    @Autowired
    public UserService(UserSimpleRepository userSimpleRepository) {
        this.userSimpleRepository = userSimpleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSimple userSimple = userSimpleRepository.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException("No user found " + username));

        //added
        /*
        List<GrantedAuthority> authorities = userSimple.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());*/

        return new org.springframework.security.core.userdetails.User(
                userSimple.getUserName(),
                userSimple.getPassword(),
                true, true, true, true,
                getAuthorities("ROLE_USER")); ///changed;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role_user) {
        return Collections.singletonList(new SimpleGrantedAuthority(role_user));
    }





}
