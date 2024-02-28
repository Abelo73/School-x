package com.act.school_xx.repository;

import com.act.school_xx.models.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursesRepository extends JpaRepository<Courses,Long> {
    List<Courses> findByGradeId(Long id);
}
