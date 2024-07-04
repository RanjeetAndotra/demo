package com.email.emailProject.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_Name")
    private String userName;
    @Column(name = "user_Email")
    private  String userEmail;
    @Column(name = "user_Password")
    private String  userPassword;
    @Column(name = "user_isActive")
    private Boolean isActive;

    private String otp;

    private Boolean isVerified;


    private LocalDateTime otpGeneratedTime;


    @CreationTimestamp
    @Column(name = "user_CreatedAt",updatable = false)
    private LocalDateTime userCreatedAt;
    @UpdateTimestamp
    @Column(name = "user_UpdatedAt")
    private LocalDateTime userUpdatedAt;


}
