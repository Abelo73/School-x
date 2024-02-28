package com.act.school_xx.controllers.controller;

import com.act.school_xx.models.FieldOfStudy;
import com.act.school_xx.services.FieldOfStudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fieldOfStudy")
public class FieldOfStudyController {

    private final FieldOfStudyService fieldOfStudyService;

    @PostMapping("/create")
    public ResponseEntity<FieldOfStudy> createFieldOfStudy(@RequestBody FieldOfStudy fieldOfStudy) {
        FieldOfStudy createdFieldOfStudy = fieldOfStudyService.createFieldOfStudy(fieldOfStudy);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFieldOfStudy);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FieldOfStudy>> getAllFieldOfStudies() {
        List<FieldOfStudy> fieldOfStudies = fieldOfStudyService.getAllFieldOfStudies();
        return ResponseEntity.ok(fieldOfStudies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldOfStudy> getFieldOfStudyById(@PathVariable Long id) {
        FieldOfStudy fieldOfStudy = fieldOfStudyService.getFieldOfStudyById(id);
        return ResponseEntity.ok(fieldOfStudy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFieldOfStudyById(@PathVariable Long id){
        fieldOfStudyService.deleteFieldOfStudyById(id);
        return ResponseEntity.ok("Deleted successfully");
    }

}
