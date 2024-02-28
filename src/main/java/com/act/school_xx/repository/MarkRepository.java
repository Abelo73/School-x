package com.act.school_xx.repository;

import com.act.school_xx.models.Courses;
import com.act.school_xx.models.Mark;
import com.act.school_xx.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findByTeacherId(Long teacherId);

    Iterable<? extends Mark> findByCoursesAndTeacher(Courses course, Teacher teacher);
}
