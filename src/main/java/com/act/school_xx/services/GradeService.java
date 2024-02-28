package com.act.school_xx.services;

import com.act.school_xx.models.Grade;
import com.act.school_xx.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    public Grade createGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
    }

    public Grade updateGrade(Long id, Grade grade) {
        grade.setId(id);
        return gradeRepository.save(grade);
    }

    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }
}
