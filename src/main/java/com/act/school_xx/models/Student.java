package com.act.school_xx.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_semester",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "semester_id")
    )
    List<Semester> semesters;

    // Reference to Section entity
    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

//
//    @ManyToOne
//    @JoinColumn(name = "courses_id")
//    private Courses courses;



    @ManyToMany
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Courses> courses = new ArrayList<>();


//    @ManyToMany
//    @JoinTable(
//            name = "student_teacher",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "teacher_id")
//    )
//    private Teacher teacher;


//    @OneToMany(mappedBy = "student")
//    private List<Mark> marks = new ArrayList<>();
}


