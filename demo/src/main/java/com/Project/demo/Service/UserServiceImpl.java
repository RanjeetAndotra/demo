package com.Project.demo.Service;
import com.Project.demo.Model.Projection.UserProjection;
import com.Project.demo.Model.Request.SaveUserRequest;
import com.Project.demo.Model.Response.PageDto;
import com.Project.demo.Model.Response.UserResponse;
import com.Project.demo.Model.User;
import com.Project.demo.Repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public String saveorupdate(SaveUserRequest saveUserRequest) throws Exception {
        if (userRepository.existsById(saveUserRequest.getUserId())) {
            User user = userRepository.findById(saveUserRequest.getUserId()).get();
            user.setUserFirstName(saveUserRequest.getUserFirstName());
            user.setUserLastName(saveUserRequest.getUserLastName());
            user.setUserEmail(saveUserRequest.getUserEmail());
            user.setUserPassword(saveUserRequest.getUserPassword());
            user.setUserAddress(saveUserRequest.getUserAddress());
            //   Object userImage= this.fileUpload(saveUserRequest.getUserImage());
            //   user.setUserImage((String) userImage);
            if (saveUserRequest.getUserImage() != null) {
                user.setUserImage((String) this.fileUpload(saveUserRequest.getUserImage()));
            }

            user.setUserIsActive(true);
            user.setUserIsDeleted(false);
            userRepository.save(user);
            return "updated successfully";
        } else {
            User user = new User();
            user.setUserId(saveUserRequest.getUserId());
//            user.setUserFirstName(saveUserRequest.getUserFirstName());
//            user.setUserLastName(saveUserRequest.getUserLastName());
//            user.setUserEmail(saveUserRequest.getUserEmail());
//            user.setUserPassword(saveUserRequest.getUserPassword());
////            Object userImage= this.fileUpload(saveUserRequest.getUserImage());
////            user.setUserImage((String) userImage);
//            String otp = generateOtp();
//            System.out.println("OTP: " + otp);
//            user.setOtp(otp);
//            user.setUserAddress(saveUserRequest.getUserAddress());
//            user.setOtpGeneratedTime(LocalDateTime.now());
//            user.setUserIsActive(true);
//            user.setUserIsDeleted(false);
//            userRepository.save(user);
//            this.sendMail("application", otp + " " + "is your gmail verification code.", saveUserRequest.getUserEmail());
//           throw  new  Exception("user created");
//           // return "user Created";

            user.setUserFirstName(saveUserRequest.getUserFirstName());
            user.setUserLastName(saveUserRequest.getUserLastName());
            user.setUserEmail(saveUserRequest.getUserEmail());
            user.setUserPassword(saveUserRequest.getUserPassword());
            user.setUserAddress(saveUserRequest.getUserAddress());
            //  Object userImage= this.fileUpload(saveUserRequest.getUserImage());
            if (saveUserRequest.getUserImage() != null) {
                user.setUserImage((String) this.fileUpload(saveUserRequest.getUserImage()));
            }

            user.setUserIsActive(true);
            user.setUserIsDeleted(false);
            String otp = generateOtp();
            System.out.println("OTP: " + otp);
            user.setOtp(otp);
            user.setOtpGeneratedTime(LocalDateTime.now());
            //  this.sendMail("application", otp + " " + "is your gmail verification code.", saveUserRequest.getUserEmail());
            userRepository.save(user);

            return "created successfully";

        }

        }

    @Override
    public User getById(Long userId) throws Exception {
        if (userRepository.existsById(userId)) {
            User user = userRepository.findById(userId).get();
            return user;
        } else {
            throw new Exception("id not exist");
        }
    }

    @Override
    public String deleteById(Long userId) throws Exception {
        if (userRepository.existsById(userId)) {
            User user = userRepository.findById(userId).get();
            userRepository.deleteById(userId);
            user.setUserIsDeleted(true);
//             userRepository.save(user);
            return "user is deleted";
        } else {
            throw new Exception("id not exist");
        }

    }

    @Override
    public String changeStatus(Long userId) throws Exception {
        if (userRepository.existsById(userId)) {
            User user = userRepository.findById(userId).get();
            if (user.getUserIsActive()) {
                user.setUserIsActive(false);
                userRepository.save(user);
                return "user inactive";
            } else {
                user.setUserIsActive(true);
                userRepository.save(user);
                return "user active";
            }
        }
        throw new Exception("user not found");
    }

    @Override
    public List<User> getAll() {
        List<User> all = userRepository.findAll();
        return all;
    }

    @Override
    public Page<User> getall(Pageable pageable) {
        Page<User> all = userRepository.findAll(pageable);
        return all;
    }

    @Override
    public List<User> sorting(String field) {



        List<User> all = userRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        return all;
    }


    @Override
    public Object changePassword(Long userId, String oldPassword, String newPassword) throws Exception {
        User user = userRepository.findById(userId).get();
        String databasePassword = user.getUserPassword();
        if (databasePassword.matches(oldPassword)) {
            if (databasePassword.matches(newPassword)) {
                throw new Exception("old and new password are same");
            } else {
                user.setUserPassword(newPassword);
                userRepository.save(user);
                return user;
            }
        } else {
            throw new Exception("old password is incorrect");

        }

    }

    @Override
    public List<SaveUserRequest> getByUserFirstName(String keyword, Pageable pageable) throws Exception {
        if (keyword != null  && !keyword.isEmpty()) {
            Page<User> users = this.userRepository.findByUserFirstName(keyword, pageable);

            List<SaveUserRequest> saveUserRequests = users.stream().map(user -> this.modelMapper.map(user, SaveUserRequest.class)).collect(Collectors.toList());
            return saveUserRequests;

        } else {
            throw new Exception("keyword is empty");
        }
    }

    @Override
    public Object getByUserLastName(Pageable pageable) {
        Page<UserProjection> projections = userRepository.findByUserLastName(pageable);
        return projections;
    }

    @Override
    public Object getAll(Pageable pageable) {
        Page<User> all = userRepository.findAll(pageable);
        return new PageDto(all.getContent(), all.getTotalPages(), all.getTotalElements(), all.getNumber());
    }

//    @Override
//    public Object getByDate(String startDate, String endDate) throws Exception {
//      if(StringUtils.isNotBlank(startDate)&& StringUtils.isNotBlank(endDate)){
//          LocalDate startDate1 = LocalDate.parse(startDate);
//          LocalDate endDate1 = LocalDate.parse(endDate);
//          LocalDateTime startDateTime = LocalDateTime.of(startDate1, LocalTime.of(0, 0));
//          LocalDateTime endDateTime = LocalDateTime.of(endDate1, LocalTime.of(23, 59));
//          List<User> users = userRepository.findByDateFilter(startDateTime, endDateTime);
//          return users;
//      }else{
//          throw new Exception("user not found");
//      }
//
//
//    }

    @Override
    public Object startDateEndDateUser(String startDate, String endDate, Pageable pageable) throws Exception {

        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            LocalDate startDate1 = LocalDate.parse(startDate);
            LocalDate endDate1 = LocalDate.parse(endDate);

            LocalDateTime startDateTime = LocalDateTime.of(startDate1, LocalTime.of(0, 0));
            LocalDateTime endDateTime = LocalDateTime.of(endDate1, LocalTime.of(23, 59));
            Page<User> users = userRepository.getStartDateAndEndDate(startDateTime, endDateTime, pageable);
            return users;

        } else {
            throw new Exception("user not found");
        }
    }


    @Override
    public Object fileUpload(MultipartFile userFile) throws Exception {
        String originalFilename = userFile.getOriginalFilename();
            if (!userFile.isEmpty() && userFile != null) {
                String storagePath = "C:\\Users\\OMS_Pune\\Downloads\\fileUpload";
                Path path = Paths.get(storagePath, originalFilename);
                Files.write(path, userFile.getBytes());
            }
            return originalFilename;
        }
//
//    @Override
//    public Object getVerifyOtpUser(String verifyOtp, Long userId) throws Exception {
//
//        User user = userRepository.findById(userId).get();
//        String otpDatabase = user.getOtp();
////        UserResponse userResponse= new UserResponse();
//        if (verifyOtp.matches(otpDatabase)) {
////            userResponse.setResponseData(user);
////            userResponse.setStatus("verified");
////            userResponse.setEmail(user.getUserEmail());
////            return userResponse;
//            return user;
//
//
//        } else {
//            throw new Exception(" not verify");
//        }


//    @Override
//    public void verify(String email, String otp) {
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            throw new RuntimeException("User not Found");
//        } else if (user.isVerified()) {
//            throw new RuntimeException("user is already verified");
//        } else if (otp.equals(user.getOtp())) {
//            user.setVerified(true);
//            userRepository.save(user);
//
//        } else {
//            throw new RuntimeException("internal server error");
//        }

//    }

    public void sendMail(String subject, String msg, String to) {
        //JavaMailSender javaMailSender = null;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setTo(to);
        message.setText(msg);
        javaMailSender.send(message);
    }

    public String generateOtp() {
        int randomNo = (int) (Math.random() * 900000 + 100000);
        String otp = String.valueOf(randomNo);
        return otp;
    }
    @Override
    public Object getVerifyOtpUsers(String verifyOtp, String userEmail) throws Exception {
        User user = userRepository.findByUserEmail(userEmail);
        if (user != null) {
            String otpDatabase = user.getOtp();
            if (verifyOtp.matches(otpDatabase)) {
                return user;
            } else {
                throw new Exception("OTP verification failed");
            }
        } else {
            throw new Exception(" User with email " + userEmail + " not found");
        }

    }

    @Override
    public InputStream getResouces(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+ File.separator+fileName;
        InputStream is = new FileInputStream(fullPath);

        return is;

    }


    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        

            // file name
            String originalFilename =file.getOriginalFilename();

            // random file generate

            String randomId = UUID.randomUUID().toString();
            String fileName1 = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

            // full path

            String filepath=path+ File.separator+fileName1;


            //create folder if not created
            File f = new File(path);
            if(!f.exists()){
                f.mkdir();
            }
            // file copy
            Files.copy(file.getInputStream(), Paths.get(filepath));

            return originalFilename;
        }

    }

