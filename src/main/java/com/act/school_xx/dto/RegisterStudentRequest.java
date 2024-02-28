package com.act.school_xx.dto;

import com.act.school_xx.models.Semester;
import lombok.Data;

import java.util.List;

@Data
public class RegisterStudentRequest {
    private Long courseId;
//    private Long semesterId;
//    private List<Long> semesterId;
    private Long semesterId;
    private Long sectionId;
    private Long gradeId;

}
