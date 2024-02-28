package com.act.school_xx.controllers.controller;

import com.act.school_xx.dto.MarkDTO;
import com.act.school_xx.services.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marks")
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;

    @PostMapping("/add")
    public ResponseEntity<MarkDTO> addMark(@RequestBody MarkDTO markDTO) {
        MarkDTO addedMark = markService.addMark(markDTO);
        if (addedMark != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(addedMark);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<MarkDTO>> getMarksByTeacherId(@PathVariable Long teacherId) {
        List<MarkDTO> marks = markService.getMarksByTeacherId(teacherId);
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MarkDTO>> getAllMarksWithDetails() {
        List<MarkDTO> marks = markService.getAllMarksWithDetails();
        return ResponseEntity.ok(marks);
    }


}
