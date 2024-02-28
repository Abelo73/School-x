package com.act.school_xx.dto;

import com.act.school_xx.models.Mark;
import lombok.Data;

import java.util.List;

@Data
public class MarkDTO {
//    private Long id;
//    private Long studentId;
//    private Long coursesId;
//    private Long teacherId;
//    private int marks;

    private Long markId;
    private Long studentId;
    private String studentName;
    private Long coursesId;
    private String courseTitle;
    private Long teacherId;
    private String teacherName;
//    private int marks;
    private Mark mark;

    private List<CoursesDTO> coursesDTO;
    private TeacherInfoDTO teacherInfoDTO;
//
//    private String studentName;
//    private String courseTitle;
//    private String teacherName;
//    private List<Integer> marks; // List of marks for each course




    // Getters and setters
}
