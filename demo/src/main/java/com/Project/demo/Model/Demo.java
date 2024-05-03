package com.Project.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "demo")
public class Demo {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long demoId;

    @Column(name = "userId")
    private String userId;

    @Column(name = "id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

}
