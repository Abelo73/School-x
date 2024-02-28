package com.act.school_xx.controllers.controller;

import com.act.school_xx.models.Section;
import com.act.school_xx.services.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @GetMapping
    public ResponseEntity<List<Section>> getAllSections() {
        List<Section> sections = sectionService.getAllSections();
        return ResponseEntity.ok(sections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable Long id) {
        Section section = sectionService.getSectionById(id);
        return ResponseEntity.ok(section);
    }

    @PostMapping
    public ResponseEntity<Section> createSection(@RequestBody Section section) {
        Section createdSection = sectionService.createSection(section);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable Long id, @RequestBody Section section) {
        Section updatedSection = sectionService.updateSection(id, section);
        return ResponseEntity.ok(updatedSection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return ResponseEntity.noContent().build();
    }
}
