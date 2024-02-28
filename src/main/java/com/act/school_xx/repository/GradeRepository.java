package com.act.school_xx.repository;

import com.act.school_xx.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
//    Optional<Grade> findById(Long id);

//    Grade getOne(List<Long> gradeId);

//    Optional<Object> findById(List<Long> gradeId);

    Grade getById(Long id);

}
