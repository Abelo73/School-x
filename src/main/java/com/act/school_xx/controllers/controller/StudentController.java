package com.act.school_xx.controllers.controller;

import com.act.school_xx.dto.StudentInfoDTO;
import com.act.school_xx.dto.StudentRegistrationRequest;
import com.act.school_xx.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody StudentRegistrationRequest request) {
        try {
            studentService.registerAsStudent(request.getUserId(), request);
            return ResponseEntity.ok("Student registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register student: " + e.getMessage());
        }
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<StudentInfoDTO>> getAllStudents() {
        List<StudentInfoDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentInfoDTO>> searchStudents(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String mobile
    ) {
        try {
            // Call the service method to search for students
            List<StudentInfoDTO> students = studentService.searchStudents(firstName, lastName, email, mobile);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }






}
