package com.email.emailProject.service;

import org.thymeleaf.context.Context;

public interface SendHtmlTemplateToMail {
    public void sendTemplateEmail(String to, String subject, String templateName, Context context);
}
