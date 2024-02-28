package com.act.school_xx.controllers.controller;

import com.act.school_xx.models.Semester;
import com.act.school_xx.services.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/semester")
public class SemesterController {

    private final SemesterService semesterService;

    @PostMapping("/add")
    public ResponseEntity<Semester> addSemester(@RequestBody Semester semester) {
        Semester addedSemester = semesterService.addSemester(semester);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedSemester);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Semester> updateSemester(@PathVariable Long id, @RequestBody Semester updatedSemester) {
        Semester updated = semesterService.updateSemester(id, updatedSemester);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Semester>> getAllSemesters() {
        List<Semester> semesters = semesterService.getAllSemesters();
        return ResponseEntity.ok(semesters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Semester> getSemesterById(@PathVariable Long id) {
        Semester semester = semesterService.getSemesterById(id);
        return ResponseEntity.ok(semester);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
        semesterService.deleteSemester(id);
        return ResponseEntity.noContent().build();
    }
}
