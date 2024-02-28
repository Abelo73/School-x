package com.act.school_xx.services;

import com.act.school_xx.models.Courses;
import com.act.school_xx.models.Grade;
import com.act.school_xx.models.Teacher;
import com.act.school_xx.repository.CoursesRepository;
import com.act.school_xx.repository.GradeRepository;
import com.act.school_xx.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoursesService {

    private final CoursesRepository coursesRepository;
    private final GradeRepository gradeRepository;
    private final TeacherRepository teacherRepository;

    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }

    public Courses getCourseById(Long id) {
        return coursesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + id));
    }

    public void deleteCourse(Long id) {
        coursesRepository.deleteById(id);
    }



    public Courses addCourse(Courses courses) {

        return coursesRepository.save(courses);
    }


//    public List<Courses> addCoursesForGrade(Long id, List<Courses> courses) {
//        Grade grade = gradeRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Grade not found with ID: " + id));
//
//        for (Courses course : courses) {
//            course.setGrade(grade);
//        }
//
//        return coursesRepository.saveAll(courses);
//    }



    public List<Courses> addCoursesForGrade(Long gradeId, List<Courses> courses, List<Courses> teacherIds) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new IllegalArgumentException("Grade not found with ID: " + gradeId));

        if (courses.size() != teacherIds.size()) {
            throw new IllegalArgumentException("Number of courses and teacher IDs must match");
        }

        for (int i = 0; i < courses.size(); i++) {
            Courses course = courses.get(i);
            Long teacherId = teacherIds.get(i).getId();

            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + teacherId));

            course.setGrade(grade);
            course.setTeacher(teacher);
            coursesRepository.save(course); // Save each course individually
        }

        return courses;
    }



    public boolean isValidCourses(Long courseId) {
        return coursesRepository.existsById(courseId);
    }

//    public Courses updateCourse(Courses updatedCourse) {
//        return coursesRepository.save(updatedCourse);
//    }

//    public Courses updateCourse(Long id, Courses updatedCourse) {
//        Optional<Courses> existingCourseOptional = coursesRepository.findById(id);
//        if (existingCourseOptional.isPresent()) {
//            Courses existingCourse = existingCourseOptional.get();
//            // Update the fields of the existing course with the fields from the updated course
//            existingCourse.setCoursesTitle(updatedCourse.getCoursesTitle());
//            // Update other fields as needed
//            return coursesRepository.save(existingCourse);
//        } else {
//            throw new IllegalArgumentException("Course not found with ID: " + id);
//        }
//    }

    public Courses updateCourse(Long id, Courses updatedCourse) {
        Courses existingCourse = coursesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + id));

        // Update the fields of the existing course with the values from the updated course
        existingCourse.setCoursesTitle(updatedCourse.getCoursesTitle());
        existingCourse.setGrade(updatedCourse.getGrade());
        existingCourse.setTeacher(updatedCourse.getTeacher());
        existingCourse.setStudents(updatedCourse.getStudents());

        // Save the updated course
        return coursesRepository.save(existingCourse);
    }


    // Method for deleting a course
//    public void deleteCourse(Long id) {
//        coursesRepository.deleteById(id);
//    }
}
