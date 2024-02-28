package com.act.school_xx.controllers;

import com.act.school_xx.models.Grade;
import com.act.school_xx.services.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grades")
public class GradeController {

    private final GradeService gradeService;

    @PostMapping("/create")
    public ResponseEntity<Grade> createGrade(@RequestBody Grade grade) {
        Grade createdGrade = gradeService.createGrade(grade);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrade);
    }

    @GetMapping("getGrades/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable Long id) {
        Grade grade = gradeService.getGradeById(id);
        return ResponseEntity.ok(grade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade grade) {
        Grade updatedGrade = gradeService.updateGrade(id, grade);
        return ResponseEntity.ok(updatedGrade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}
