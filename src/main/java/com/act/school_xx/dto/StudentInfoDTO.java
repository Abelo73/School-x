package com.act.school_xx.dto;

import com.act.school_xx.models.Role;
import com.act.school_xx.models.Semester;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class StudentInfoDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String mobile;
    private int age;
    private Role role;
    private LocalDateTime registrationDate;
    private List<SemesterDTO> semesterDTO;
    private List<CoursesDTO> courses;
    private SectionDTO sectionDTO;
    private GradeDTO gradeDTO;


}
