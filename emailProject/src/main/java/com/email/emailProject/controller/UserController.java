package com.email.emailProject.controller;

import com.email.emailProject.model.request.UserRequest;
import com.email.emailProject.model.response.EntityResponse;
import com.email.emailProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/saveorupdate")
    public ResponseEntity<?>saveorupdate(@RequestBody UserRequest userRequest){
        try{
            return new ResponseEntity(new EntityResponse(userService.saveorupdate(userRequest),0), HttpStatus.OK);


            } catch (Exception e) {
           return new ResponseEntity(new EntityResponse(e.getMessage(),-1),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/htmlToPdf")
      public ResponseEntity<?>htmlToPdf(@RequestParam String email){
        try {
            return new ResponseEntity<>(new EntityResponse(userService.htmlToPdf(email),0), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(new EntityResponse(e.getMessage(),-1),HttpStatus.BAD_REQUEST);
        }

    }
}


