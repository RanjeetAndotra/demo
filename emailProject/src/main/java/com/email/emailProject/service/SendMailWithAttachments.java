package com.email.emailProject.service;

import javax.mail.MessagingException;
import java.io.IOException;

public interface SendMailWithAttachments {

    public void sendMailAttachments(String toEmail,String subject,String body, String filePath);



}
