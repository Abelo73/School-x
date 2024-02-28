package com.act.school_xx.controllers.controller;

import com.act.school_xx.models.Courses;
import com.act.school_xx.services.CoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CoursesController {

    private final CoursesService courseService;



//    @PostMapping("/add")
//    public ResponseEntity<Courses> addCourse(@RequestBody Courses course) {
//        Courses createdCourse = courseService.addCourse(course);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
//    }

    @PostMapping("/add")
    public ResponseEntity<Courses> addCourse(@RequestBody Courses course) {
        Courses createdCourse = courseService.addCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

//    @PostMapping("/addCoursesForGrade/{id}")
//    public ResponseEntity<List<Courses>> addCoursesForGrade(@PathVariable Long id, @RequestBody List<Courses> courses) {
//        List<Courses> addedCourses = courseService.addCoursesForGrade(id, courses);
//        return ResponseEntity.status(HttpStatus.CREATED).body(addedCourses);
//    }

    @PostMapping("/addCoursesForGrade/{gradeId}")
    public ResponseEntity<List<Courses>> addCoursesForGrade(@PathVariable Long gradeId, @RequestBody List<Courses> teacherId, @RequestBody List<Courses> courses) {
        List<Courses> addedCourses = courseService.addCoursesForGrade(gradeId, teacherId, teacherId);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCourses);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Courses>> getAllCourses() {
        List<Courses> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Courses> getCourseById(@PathVariable Long id) {
        Courses course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }


    // Endpoint for updating a course
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable("id") Long id, @RequestBody Courses updatedCourse) {
        Courses course = courseService.getCourseById(id);
        if (course != null) {
            updatedCourse.setId(id); // Make sure the ID matches
            Courses updated = courseService.updateCourse(id, updatedCourse);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint for deleting a course
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id) {
//        courseService.deleteCourse(id);
//        return ResponseEntity.noContent().build();
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Courses> updateCourse(@PathVariable Long id, @RequestBody Courses courses) {
//        Courses updatedCourse = courseService.updateCourse(id, courses);
//        return ResponseEntity.ok(updatedCourse);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
