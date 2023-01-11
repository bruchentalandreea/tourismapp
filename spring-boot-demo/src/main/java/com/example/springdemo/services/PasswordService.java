package com.example.springdemo.services;

import com.example.springdemo.entities.Mail;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private final MailService mailService;

    public PasswordService(MailService mailService) {
        this.mailService = mailService;
    }

    @SneakyThrows
    public void sendEmail(String user){
        mailService.sendMail(new Mail("More information",
                user, "Tourism :\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam pharetra turpis elit, vitae dapibus lorem congue nec. Aenean ultricies mi vel metus convallis pharetra. Etiam vestibulum id purus ut ornare. Fusce in finibus sapien. Aliquam at tincidunt eros. Etiam a sem quis elit varius iaculis. Pellentesque pharetra congue commodo. Morbi pellentesque tristique nibh in vulputate. Quisque et commodo leo. Pellentesque nec lacinia massa. Sed eget arcu porttitor, placerat massa sit amet, dapibus neque. In lectus orci, convallis ut ex sed, faucibus sodales tortor. Phasellus sit amet sapien ante." ));
    }
}
