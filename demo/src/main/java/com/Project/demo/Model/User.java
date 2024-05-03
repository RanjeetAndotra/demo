package com.Project.demo.Model;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "user_FirstName")
    private String userFirstName;
    @Column(name = "user_LastName")
    private String userLastName;
    @Column(name = "user_Email")
    private String userEmail;
    @Column(name = "user_Password")
    private String userPassword;
    @Column(name = "OTP")
    private String otp;
    @Column(name = "user_Address")
    private String userAddress;
    @Column(name = "user_IsActive")
    private Boolean userIsActive;
    @Column(name = "user_Verified")
    private Boolean verified;
    @Column(name = "user_IsDeleted")
    private Boolean userIsDeleted;
    @Column(name = "otpGeneratedTime")
    private LocalDateTime otpGeneratedTime;
    @Column(name = "active")
    private Boolean active;

    private String userImage;


    @CreationTimestamp
    @Column(name = "user_CreatedAt",updatable = false)
    private LocalDateTime userCreatedAt;
    @UpdateTimestamp
    @Column(name = "user_UpdatedAt")
    private LocalDateTime userUpdatedAt;


}
