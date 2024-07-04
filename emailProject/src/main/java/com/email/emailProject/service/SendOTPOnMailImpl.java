package com.email.emailProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendOTPOnMailImpl implements SendOTPOnMail{

   @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
        String link = String.format("http://localhost:9191/api/verifyOTP?email=%s&otp=%s", email, otp);
        String htmlContent = String.format("<div><a href='%s' target='_blank'>Click link to verify</a></div>", link);
        mimeMessageHelper.setText(htmlContent, true);
        javaMailSender.send(mimeMessage);




    }
}


