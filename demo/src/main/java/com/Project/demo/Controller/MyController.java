package com.Project.demo.Controller;
import com.Project.demo.Model.Request.SaveUserRequest;
import com.Project.demo.Model.Response.EntityResponse;
import com.Project.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MyController {
    @Autowired
    private UserService userService;


    @Value("${project.image}")
    private String path;

    @GetMapping("/firstapi")
    public String myFirstapi() {
        return "working";
    }

//    @PostMapping("/saveorupdate")
//    public ResponseEntity<?> saveorupdate(@Valid @RequestBody SaveUserRequest saveUserRequest) {
//        try {
//            {
//                return new ResponseEntity(new EntityResponse(userService.saveorupdate(saveUserRequest), 0), HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.BAD_REQUEST);
//        }

        @PostMapping("/saveorupdate")
        public ResponseEntity<?> saveorupdate(@ModelAttribute SaveUserRequest saveUserRequest) throws Exception {
            try {

                    return new ResponseEntity(new EntityResponse(userService.saveorupdate(saveUserRequest), 0), HttpStatus.OK);
                }
             catch (Exception e) {
                return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.BAD_REQUEST);
            }
        }

    @GetMapping("/getById")
    public ResponseEntity<?> getById(@RequestParam Long userId) {
        try {
            return new ResponseEntity(new EntityResponse(userService.getById(userId), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(@RequestParam Long userId) {
        try {
            return new ResponseEntity(new EntityResponse(userService.deleteById(userId), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestParam Long userId) {
        try {
            return new ResponseEntity(new EntityResponse(userService.changeStatus(userId), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity(new EntityResponse(userService.getAll(), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);

        }

    }

    @GetMapping("/getall")
    public ResponseEntity<?> getall(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        try {
            return new ResponseEntity(new EntityResponse(userService.getall(pageable), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/sorting")
    public ResponseEntity<?> sorting(@RequestParam String field) {
        try {
            return new ResponseEntity(new EntityResponse(userService.sorting(field), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestParam Long userId, @Valid @RequestParam String oldPassword, @Valid @RequestParam String newPassword) {
        try {
            return new ResponseEntity(new EntityResponse(userService.changePassword(userId, oldPassword, newPassword), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getByFirstName")
    public ResponseEntity<?> getByUserFirstName(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                @RequestParam String keyword) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try {

            return new ResponseEntity(new EntityResponse(userService.getByUserFirstName(keyword, pageable), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getByUserLastName")
    public ResponseEntity<?> getByuserlastName(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {

        try {
            Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0), Optional.ofNullable(pageSize).orElse(10));
            return new ResponseEntity(new EntityResponse(userService.getByUserLastName(pageable), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getalll")
    public ResponseEntity<?> getalll(@RequestParam(name = "pageNo", defaultValue = "3") int pageNo,
                                     @RequestParam(name = "pageSize", defaultValue = "1") int pageSize,
                                     @RequestParam String SortBy,
                                     @RequestParam String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(SortBy).ascending() : Sort.by(SortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        try {
            return new ResponseEntity<>(new EntityResponse(userService.getAll(pageable), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }

    }


    //    @GetMapping("/byDate")
//    public ResponseEntity<?>getByDate(@RequestParam  String startDate, String endDate){
//        try{
//            return new ResponseEntity<>(new EntityResponse(userService.getByDate(startDate,endDate),0),HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.NOT_FOUND);
//        }
//    }
    @PostMapping("/startDateEndDate")
    public ResponseEntity<?> startDateEndDateUser(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                  @RequestParam(required = false) String startDate,
                                                  @RequestParam(required = false) String endDate) {
        try {
            Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0), Optional.ofNullable(pageSize).orElse(10));
            return new ResponseEntity(new EntityResponse(userService.startDateEndDateUser(startDate, endDate, pageable), 0), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/fileUpload")
    public ResponseEntity<?> fileUpload(@ModelAttribute("userFile") MultipartFile userFile) {
        try {
            return new ResponseEntity<>(new EntityResponse(userService.fileUpload(userFile), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }
    }
//
//    @PostMapping("/getVerifyOtp")
//    public ResponseEntity<?> getverifyOptuser(@RequestParam String verifyOtp, @RequestParam Long userId) {
//        try {
//            return new ResponseEntity(new EntityResponse(userService.getVerifyOtpUser(verifyOtp, userId), 0), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
//        }
//    }


    //    @PostMapping("/verify")
//    public ResponseEntity<?> verifyUser(@RequestParam String email, @RequestParam String otp) {
//       try {
//           userService.verify(email, otp);
//            return new ResponseEntity("user verified Successfully", HttpStatus.OK);
//        } catch (Exception e) {
//           return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
//       }
//
//
//    }
    @PutMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(@RequestParam String verifyOtp, @RequestParam String userEmail) throws Exception {
       try {

           return new ResponseEntity<>(new EntityResponse(userService.getVerifyOtpUsers(verifyOtp, userEmail),0),HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.BAD_REQUEST);
       }
       
    }

    @PostMapping("/upload")
    public ResponseEntity<?>fileUploads(@RequestParam("image")MultipartFile image){
        String fileName = null;
        try {
            fileName = this.userService.uploadImage(path, image);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new EntityResponse("image not ",-1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new EntityResponse(fileName+ "image uploaded",0), HttpStatus.OK);
    }

}