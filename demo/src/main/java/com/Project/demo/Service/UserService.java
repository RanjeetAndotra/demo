package com.Project.demo.Service;
import com.Project.demo.Model.Request.SaveUserRequest;
import com.Project.demo.Model.Response.PageDto;
import com.Project.demo.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface UserService {

    String saveorupdate(SaveUserRequest  saveUserRequest) throws Exception;

    User getById(Long userId) throws Exception;

    String deleteById(Long userId) throws Exception;

    String changeStatus(Long userId)throws Exception;

    List<User>getAll();

   Object getall(Pageable pageable);

    List<User>sorting(String field);

    Object changePassword(Long userId , String oldPassword,String newPassword) throws Exception;

    List<SaveUserRequest> getByUserFirstName(String keyword,Pageable pageable)throws Exception;

    Object getByUserLastName(Pageable pageable);

  Object getAll(Pageable pageable);

//    Object getByDate(String startDate, String endDate) throws Exception;


    Object startDateEndDateUser(String startDate, String endDate, Pageable pageable) throws Exception;



    Object fileUpload(MultipartFile userFile) throws Exception;



//    Object getVerifyOtpUser(String verifyOtp, Long userId ) throws Exception;

//    Object verifyAccount(String email, String otp);

     Object getVerifyOtpUsers(String verifyOtp, String userEmail) throws Exception;

//     void verify(String email,String otp);


    InputStream getResouces(String path, String fileName) throws FileNotFoundException;

    String uploadImage(String path, MultipartFile file) throws IOException;



}
