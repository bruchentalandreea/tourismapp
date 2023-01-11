package com.example.springdemo.services;

import com.example.springdemo.SpringDemoApplicationTests;

public class UserServiceTest extends SpringDemoApplicationTests {
/*
    @Autowired
    UserService userService;
    User user;

    @Test(expected = IncorrectParameterException.class)
    public void insertDTOBad() {
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCreated_at());
        userDTO.setEmail("john.patterson.gmail");
        userService.insert(userDTO);

    }
    */

    /*
    @Test
    public void insertDTOGood() {
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCreated_at());
        userDTO.setName("John Patterson");
        userDTO.setEmail("john.patterson@gmail.com");
        userDTO.setPassword("123456");
        userDTO.setCreated_at.getTime();
        Integer id = userService.insert(userDTO);
        UserViewDTO person2 = userService.findPersonById(id);
        assert(!userDTO.equals(person2));

    }*/
}
