package com.email.emailProject.service;

import com.email.emailProject.model.request.UserRequest;
import com.lowagie.text.DocumentException;

import java.io.IOException;

public interface UserService {


    Object saveorupdate(UserRequest userRequest)throws Exception;

    Object htmlToPdf(String email) throws IOException, DocumentException;
}
