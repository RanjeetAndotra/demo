package com.email.emailProject.service;

import javax.mail.MessagingException;

public interface SendOTPOnMail {
    public void sendOtpEmail(String email, String otp) throws MessagingException;
}
