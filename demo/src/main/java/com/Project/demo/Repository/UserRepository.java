package com.Project.demo.Repository;
import com.Project.demo.Model.Projection.UserProjection;
import com.Project.demo.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Page<User> findByUserFirstName(String Keyword,Pageable pageable);

     @Query(value = "select user_last_name as userLastName  from user_table",nativeQuery = true)
     Page<UserProjection> findByUserLastName(Pageable pageable);

//     @Query(value = "select * from user_table where user_created_at between :startDate and :endDate",nativeQuery = true)
//     List<User>findByDateFilter(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select * from user_table where user_created_at between :startDate and :endDate",nativeQuery = true)
    Page<User> getStartDateAndEndDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);




//    User findByEmail(String userEmail);

    User findByUserEmail(String userEmail);

}
