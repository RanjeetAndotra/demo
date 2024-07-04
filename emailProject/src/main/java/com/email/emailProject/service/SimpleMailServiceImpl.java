package com.email.emailProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleMailServiceImpl implements SimpleMailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("ranjeet.omsoftware@gmail.com")
    private String sendMailFrom;
    @Override
    public void sendMail(String toEmail, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setText(body);
        simpleMailMessage.setFrom(sendMailFrom);
        javaMailSender.send(simpleMailMessage);
    }
}
