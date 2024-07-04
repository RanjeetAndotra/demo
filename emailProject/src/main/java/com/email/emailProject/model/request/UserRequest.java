package com.email.emailProject.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {

    private Long userId;
    private String userName;
    private String userEmail;
    private String userPassword;

}
