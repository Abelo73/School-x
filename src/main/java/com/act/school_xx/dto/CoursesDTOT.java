package com.act.school_xx.dto;

import com.act.school_xx.models.EducationalLevel;
import com.act.school_xx.models.FieldOfStudy;
import com.act.school_xx.models.Role;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoursesDTOT {
    private Long id;
    private String coursesTitle;
//    private String firstName;
//    private String middleName;
//    private String lastName;
//    private Role role;
    private List<StudentDTO> students;
//    private List<MarkDTO> markDTOS;





//    private Teacher teacher;

//    public CoursesDTO(Long id, String coursesTitle) {
//        this.id = id;
//        this.coursesTitle = coursesTitle;
//        this.students = students;
//        this.markDTOS = markDTOS;
//
//    }


}