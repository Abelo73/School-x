package com.act.school_xx.dto;

import com.act.school_xx.models.Grade;
import com.act.school_xx.models.User;
import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TeacherInfoDTO {
    private Long id;
    private Long userId;
    private BigDecimal salary;
    private String fieldOfStudy;
    private String educationalLevel;
    private Long courseId;
    private User user;
    private String firstName;
    private String middleName;
    private String mobile;
    private String email;
    private int age;
    private String  lastName;
    private String coursesTitle;
    private Grade grade;

//    public void setCourseName(String courseTitle) {
//    }


    // Constructors, getters, and setters
}
