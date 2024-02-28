package com.act.school_xx.controllers.controller;

import com.act.school_xx.dto.DetailedTeacherDTO;
import com.act.school_xx.dto.TeacherDTO;
import com.act.school_xx.dto.TeacherRegistrationRequest;
import com.act.school_xx.models.Teacher;
import com.act.school_xx.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/register/{userId}")
    public ResponseEntity<String> registerTeacher(@PathVariable Long userId, @RequestBody TeacherRegistrationRequest request) {
        try {
            teacherService.registerAsTeacher(userId, request);
            return ResponseEntity.ok("Teacher registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering teacher: " + e.getMessage());
        }
    }
    @GetMapping("/getAllTeachers")
    public ResponseEntity<List<DetailedTeacherDTO>> getAllTeachers() {
        List<DetailedTeacherDTO> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDTO> getTeacherDetails(@PathVariable Long teacherId) {
        TeacherDTO teacherDetails = teacherService.getTeacherDetails(teacherId);
        if (teacherDetails != null) {
            return ResponseEntity.ok(teacherDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    @PostMapping("/register/{userId}")
//    public ResponseEntity<String> registerTeacher(@PathVariable Long userId, @RequestBody TeacherRegistrationRequest request) {
//        try {
//            teacherService.registerAsTeacher(request.getUserId(), request);
//            return ResponseEntity.ok("Teacher registered successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering teacher: " + e.getMessage());
//        }
//    }
}
