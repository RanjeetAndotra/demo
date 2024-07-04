package com.email.emailProject.controller;

import com.email.emailProject.model.User;
import com.email.emailProject.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/api")
public class VerifyUserController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/verifyAccount")
    public String verifyAccount(@RequestParam String email) throws Exception {

        User user = userRepository.findByUserEmail(email);
        Long userId = user.getUserId();
        if (userRepository.existsById(userId)) {
            user.setIsVerified(true);
            userRepository.save(user);
            return "UserSuccess";

        } else {
            return "UserFailure";
        }


    }
    @GetMapping("/verifyOTP")

  //  @ResponseBody      ( this is used when we dont need to send template as a response in postman )

    public String verifyOTP(@RequestParam String email, @RequestParam String otp) {
        User user = userRepository.findByUserEmail(email);
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (3 * 60)) {
            user.setIsActive(true);
            return "Otp verified you can login";
        } else {
            return "please regenerate otp and try again ";
        }


    }
//
//    @GetMapping("/verifyOTP")
//    public String verifyOTP(@RequestParam String email, @RequestParam(required = false) String otp) {
//        if (otp == null || otp.isEmpty()) {
//            return "Missing OTP parameter";
//        }
//
//        User user = userRepository.findByUserEmail(email);
//        if (user == null) {
//            return "User not found";
//        }
//
//        if (otp.equals(user.getOtp()) && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (1 * 60)) {
//            user.setIsActive(true);
//            userRepository.save(user);
//            return "Otp verified, you can login";
//        } else {
//            return "Please regenerate OTP and try again";
//        }
//    }

}

