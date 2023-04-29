package com.eventprogrammer.userservice.service;

import com.eventprogrammer.userservice.DTO.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService implements EmailSend{

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;


    @Override
    public void send(String to, Email email) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getMsgBody());

        javaMailSender.send(simpleMailMessage);
    }
}
