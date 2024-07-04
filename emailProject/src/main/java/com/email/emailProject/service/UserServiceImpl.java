package com.email.emailProject.service;

import com.email.emailProject.model.User;
import com.email.emailProject.model.request.UserRequest;
import com.email.emailProject.respository.UserRepository;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  SimpleMailService simpleMailService;

    @Autowired
    private SendOTPOnMail sendOTPOnMail;

    @Autowired
    private SendHtmlTemplateToMail sendHtmlTemplateToMail;

    @Autowired
    private SendMailWithAttachments sendMailWithAttachments;


    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public Object saveorupdate(UserRequest userRequest) throws Exception  {
        if(userRepository.existsById(userRequest.getUserId())){
            User user = userRepository.findById(userRequest.getUserId()).get();
            user.setUserName(userRequest.getUserName());
            user.setUserEmail(userRequest.getUserEmail());
            user.setUserPassword(userRequest.getUserPassword());
            user.setOtp(this.generateOtp());
            user.setIsActive(true);
            user.setIsVerified(false);
            user.setOtpGeneratedTime(LocalDateTime.now());
            userRepository.save(user);
            return "updated";
        }else {
            User user = new User();
            user.setUserId(userRequest.getUserId());
            user.setUserName(userRequest.getUserName());
            user.setUserEmail(userRequest.getUserEmail());
            user.setUserPassword(userRequest.getUserPassword());
            user.setOtp(this.generateOtp());
            user.setIsActive(true);
            user.setIsVerified(false);
            user.setOtpGeneratedTime(LocalDateTime.now());
            String email = userRequest.getUserEmail();
            String otp = user.getOtp();

           simpleMailService.sendMail(email,"verification Email","this is OTP + " + otp);

         //   sendMailWithAttachments.sendMailAttachments(email,"Attachments","email with attachments","C:\\Users\\OMS_Pune\\Downloads\\fileUpload\\Credentials.txt" +otp);

            sendOTPOnMail.sendOtpEmail(email,otp);


            Context context = new Context();
            context.setVariable("email",email);
//            context.setVariable("userId",userRequest.getUserId());
//            context.setVariable("success",true);
//            context.setVariable("otp",otp);

            sendHtmlTemplateToMail.sendTemplateEmail(email,"verification Mail","SignUp.html",context);
            userRepository.save(user);
            return "user created";

        }

   }



    @Override
    public MultipartFile htmlToPdf(String email) throws IOException, DocumentException {
       // LocalDate localDate = LocalDate.parse(date);
        Context  context = new Context();
       context.setVariable("email",email);
      //  context.setVariable("date",date);
        String signUp = templateEngine.process("please regenerate otp and try again ", context);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(signUp);
        renderer.layout();
        renderer.createPDF(baos);
        renderer.finishPDF();

        MultipartFile multipartFile = new MockMultipartFile(
                "Converted.pdf", // file name
                "Converted.pdf", // original file name
                "application/pdf", // content type
                baos.toByteArray()); // PDF content as byte array

        return multipartFile;


    }

    public String generateOtp(){

       Random random = new Random();
       int randomNumber = random.nextInt(999999);

       String output = Integer.toString(randomNumber);
       while (output.length()<6){
           output = "0" + output;
       }
       return output;
   }
}
