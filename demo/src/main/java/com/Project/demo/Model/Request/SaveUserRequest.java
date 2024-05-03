package com.Project.demo.Model.Request;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveUserRequest {
    private  Long userId;
    @NotEmpty
    @Size(min = 4,max = 15,message = "user first name must be minimum of 4 chars and not more than 15 chars")
    private String userFirstName;
    @NotEmpty
    @Size(max = 20,message = "last name chars not more than 20 chars")
    private String userLastName;
    @Email(message = "email is not valid")
    private String userEmail;
    @NotEmpty
    @Size(min = 4,max = 10,message = "password must be minimum of 4 and maximum of 10")
    private String userPassword;
    @NotEmpty
    private String userAddress;

    private MultipartFile userImage;



}
