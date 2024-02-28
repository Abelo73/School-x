package com.act.school_xx.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeacherDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String mobile;
    private int age;
    private String role;
    private String salary;
    private List<CoursesDTOT> courses;
    private List<SemesterDTO> semesters;
//    private List<StudentDTO> students; // New field to hold the list of students
    private FieldOfStudyDTO fieldOfStudyDTO;
    private EducationalLevelDTO educationalLevelDTO;
    private List<SectionDTO> sectionDTO;
    private List<GradeDTO> gradeDTO;
}
