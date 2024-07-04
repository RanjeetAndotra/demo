package com.email.emailProject.service;

import com.email.emailProject.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendHtmlTemplateToMailImpl implements SendHtmlTemplateToMail{
    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private JavaMailSender  javaMailSender;

    @Value("ranjeet.omsoftware@gmail.com")
    private String sendMailFrom;


    @Autowired
    private TemplateEngine templateEngine;


    @Override
    public void sendTemplateEmail(String to, String subject, String templateName, Context context) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");

        try
        {
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(sendMailFrom);
            mimeMessageHelper.setSubject(subject);

            String htmlContent  = templateEngine.process("SignUp",context);
            mimeMessageHelper.setText(htmlContent,true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {

            e.printStackTrace();
        }

    }
}
