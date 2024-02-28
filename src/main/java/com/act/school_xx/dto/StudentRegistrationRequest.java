package com.act.school_xx.dto;

import com.act.school_xx.models.Courses;
import com.act.school_xx.models.Grade;
import com.act.school_xx.models.Section;
import com.act.school_xx.models.Semester;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
public class StudentRegistrationRequest {
    private Long userId;
    private Long sectionId;
    private Long gradeId;
    private List<Long> semesterId;
    private List<Long> coursesId;
    private Long teacherId;




    // Constructors, getters, and setters
}
