package com.act.school_xx.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String salary;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "courses_id")
//    private Courses courses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "semester_id")
//    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "fieldOfStudy_id")
    private FieldOfStudy fieldOfStudy;

    // Change the data type to represent a collection of courses
    @ManyToMany(fetch = FetchType.LAZY) // Assuming a teacher can teach multiple courses
    @JoinTable(
            name = "teacher_courses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Courses> courses; // Use List<Courses>

//    @OneToMany(mappedBy = "teacher")
//    private List<Courses> courses;




//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "teacher_semester",
//            joinColumns = @JoinColumn(name = "teacher_id"),
//            inverseJoinColumns = @JoinColumn(name = "semester_id")
//    )
//    private List<Semester> semesters;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "teacher_semester",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "semester_id")
    )
    private List<Semester> semesters;

    @ManyToOne
    @JoinColumn(name = "educationalLevel_id")
    private EducationalLevel educationalLevel;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "teacher_student",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")

    )
    private List<Student> students;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "teacher_grade",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "grade_id")

    )
    private List<Grade> grade;

    @ManyToMany(fetch = FetchType.LAZY) // Assuming a teacher can teach multiple courses
    @JoinTable(
            name = "teacher_section",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "section_id")
    )
    private List<Section> sections;

    @OneToMany(mappedBy = "teacher")
    private List<Mark> marks;
}
