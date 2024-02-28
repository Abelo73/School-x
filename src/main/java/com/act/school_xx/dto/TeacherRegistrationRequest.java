package com.act.school_xx.dto;

import com.act.school_xx.models.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeacherRegistrationRequest {
    private Long userId;
    private BigDecimal salary;
    private Long fieldOfStudyId;
    private Long educationalLevelId;
    private List<Long> semesterIds;
    private List<Long> courseIds;
    private List<Long> studentId;
    private List<Long> gradeIds;
    private List<Long> sectionIds;
    private List<Long> studentIds;



//    private User user;

    // Constructors, getters, and setters can be added as needed
}
