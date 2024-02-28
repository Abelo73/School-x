package com.act.school_xx.dto;

import com.act.school_xx.models.*;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailedTeacherDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String mobile;
    private Integer age;
    private Role role;
    private String salary;
    private List<CoursesDTO> courses;
    private List<CoursesDTO> coursesDTOList;
    private List<Semester> semesters;
    private List<StudentDTO> students;
    private FieldOfStudyDTO fieldOfStudyDTO;
    private EducationalLevelDTO educationalLevelDTO;
    private List<SectionDTO> sectionDTO;
    private List<GradeDTO> gradeDTO;

    public DetailedTeacherDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.firstName = teacher.getUser().getFirstName();
        this.middleName = teacher.getUser().getMiddleName();
        this.lastName = teacher.getUser().getLastName();
        this.email = teacher.getUser().getEmail();
        this.mobile = teacher.getUser().getMobile();
        this.age = teacher.getUser().getAge();
        this.role = teacher.getUser().getRole();
        this.salary = teacher.getSalary();

//        this.educationalLevel = String.valueOf(teacher.getEducationalLevel());
        this.educationalLevelDTO = new EducationalLevelDTO(
                teacher.getEducationalLevel().getId(),
                teacher.getEducationalLevel().getEducationalLevelName()
        ); // Create EducationalLevelDTO object with id and educationalLevelName

        this.fieldOfStudyDTO = new FieldOfStudyDTO(
                teacher.getFieldOfStudy().getId(),
                teacher.getFieldOfStudy().getFieldOfStudyName()
        );
        // Check if teacher has a section associated
        if (teacher.getSection() != null) {
            this.sectionDTO = Collections.singletonList(new SectionDTO(
                    teacher.getSection().getId(),
                    teacher.getSection().getSectionName()
            ));
        } else {
            this.sectionDTO = Collections.emptyList();
        }
        this.sectionDTO = mapSections(teacher.getSections());
        this.gradeDTO = mapGrades(teacher.getGrade());
        this.courses = mapCourses(teacher.getCourses());;
        this.semesters = mapSemesters(teacher.getSemesters());
        this.students = mapStudents(teacher.getStudents()); // Populate students
    }

    private List<SectionDTO> mapSections(List<Section> sections) {
        return sections.stream()
                .map(section -> new SectionDTO(section.getId(), section.getSectionName()))
                .collect(Collectors.toList());
    }

//    private List<CoursesDTOT> mapCourses(List<Courses> courses) {
//        return courses.stream()
//                .map(course -> {
//                    CoursesDTOT coursesDTO = new CoursesDTOT();
//                    coursesDTO.setId(course.getId());
//                    coursesDTO.setCoursesTitle(course.getCoursesTitle());
//                    // Populate students for each course
////                    List<Student> students = course.getStudents();
//                    List<Student> students = course.getStudents();
//                    List<StudentDTO> studentDTOs = mapStudents(students);
//                    coursesDTO.setStudents(studentDTOs);
//                    return coursesDTO;
//                })
//                .collect(Collectors.toList());
//    }
//private List<CoursesDTOT> mapCourses(List<Courses> courses) {
//    return courses.stream()
//            .map(course -> {
//                CoursesDTOT coursesDTO = new CoursesDTOT();
//                coursesDTO.setId(course.getId());
//                coursesDTO.setCoursesTitle(course.getCoursesTitle());
//
//                // Populate students for each course
//                List<Student> students = course.getStudents();
//                List<StudentDTO> studentDTOs = mapStudents(students);
//                coursesDTO.setStudents(studentDTOs);
//
//                return coursesDTO;
//            })
//            .collect(Collectors.toList());
//}


//    private List<CoursesDTOT> mapCourses(List<Courses> courses) {
//        return courses.stream()
//                .map(course -> {
//                    CoursesDTOT courseDTO = new CoursesDTOT();
//                    courseDTO.setId(course.getId());
//                    courseDTO.setCoursesTitle(course.getCoursesTitle());
//
//                    // Populate students for each course
//                    List<Student> students = course.getStudents();
//                    List<StudentDTO> studentDTOs = mapStudents(students);
//                    courseDTO.setStudents(studentDTOs);
//
//                    return courseDTO;
//                })
//                .collect(Collectors.toList());
//    }




    private List<GradeDTO> mapGrades(List<Grade> grades) {
        if (grades != null) {
            return grades.stream()
                    .map(grade -> new GradeDTO(grade.getId(), grade.getGradeNumber()))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private List<StudentDTO> mapStudents(List<Student> students) {
        if (students != null) {
            return students.stream()
                    .map(student -> new StudentDTO(
                            student.getId(),
                            student.getUser().getFirstName(),
                            student.getUser().getMiddleName(),
                            student.getUser().getLastName()
                            // Include any other relevant student information here
                    ))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

//    private List<CoursesDTOT> mapCourses(List<Courses> courses) {
//        return courses.stream()
//                .map(course -> {
//                    CoursesDTOT coursesDTO = new CoursesDTOT();
//                    coursesDTO.setId(course.getId());
//                    coursesDTO.setCoursesTitle(course.getCoursesTitle());
//
//                    // Populate students for each course
//                    List<Student> students = course.getStudents();
//                    List<StudentDTO> studentDTOs = mapStudents(students);
//                    coursesDTO.setStudents(studentDTOs);
//
//                    return coursesDTO;
//                })
//                .collect(Collectors.toList());
//    }

//    private List<StudentDTO> mapStudents(List<Student> students) {
//        return students.stream()
//                .map(student -> new StudentDTO(
//                        student.getId(),
//                        student.getUser().getFirstName(),
//                        student.getUser().getMiddleName(),
//                        student.getUser().getLastName()
//                        // Include any other relevant student information here
//                ))
//                .collect(Collectors.toList());
//    }

    private List<CoursesDTO> mapCourses(List<Courses> courses) {
        return courses.stream()
                .map(course -> {
                    CoursesDTO coursesDTO = new CoursesDTO();
                    coursesDTO.setId(course.getId());
                    coursesDTO.setCoursesTitle(course.getCoursesTitle());

                    // Map the list of students enrolled in this course
                    List<StudentDTO> studentDTOs = mapStudents(course.getStudents());
                    coursesDTO.setStudents(studentDTOs);

                    return coursesDTO;
                })
                .collect(Collectors.toList());
    }



    private List<Semester> mapSemesters(List<Semester> semesters){
        if (semesters != null){
            return semesters.stream()
                    .map(semester -> {
//                        Grade grade = semester.getGrade(); // Get the grade associated with the semester
                        return new Semester(semester.getId(), semester.getSemesterName());
                    })
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }


}
