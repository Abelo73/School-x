package com.act.school_xx.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
//@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "semester")
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String semesterName;

    public Semester(Long id, String semesterName) {
        this.id = id;
        this.semesterName = semesterName;

    }


//
//    @ManyToMany(mappedBy = "semesters")
//    private List<Student> students;


}
