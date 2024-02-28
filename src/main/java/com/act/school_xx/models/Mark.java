package com.act.school_xx.models;

import com.act.school_xx.dto.MarkDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mark")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "courses_id")
    private Courses courses;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "user_id") // Assuming there is a column named "user_id" in the "mark" table
    private User user;

    @OneToMany(mappedBy = "mark", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarkDetail> markDetails;

    // New field to store marks
    private Integer marks;

    public void setMarks(Integer marks) {
        this.marks = marks;
    }
}
