package com.act.school_xx.controllers.controller;

import com.act.school_xx.models.EducationalLevel;
import com.act.school_xx.services.EducationalLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/educationalLevel")
public class EducationalLevelController {

    private final EducationalLevelService educationalLevelService;

    @PostMapping("/create")
    public ResponseEntity<EducationalLevel> createEducationalLevel(@RequestBody EducationalLevel educationalLevel) {
        EducationalLevel createdEducationalLevel = educationalLevelService.createEducationalLevel(educationalLevel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEducationalLevel);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EducationalLevel>> getAllEducationalLevels() {
        List<EducationalLevel> educationalLevels = educationalLevelService.getAllEducationalLevels();
        return ResponseEntity.ok(educationalLevels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationalLevel> getEducationalLevelById(@PathVariable Long id) {
        EducationalLevel educationalLevel = educationalLevelService.getEducationalLevelById(id);
        return ResponseEntity.ok(educationalLevel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationalLevel> updateEducationalLevel(@PathVariable Long id, @RequestBody EducationalLevel educationalLevel) {
        EducationalLevel updatedEducationalLevel = educationalLevelService.updateEducationalLevel(id, educationalLevel);
        return ResponseEntity.ok(updatedEducationalLevel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducationalLevel(@PathVariable Long id) {
        educationalLevelService.deleteEducationalLevel(id);
        return ResponseEntity.noContent().build();
    }
}
