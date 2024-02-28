package com.act.school_xx.services;

import com.act.school_xx.dto.CoursesDTO;
import com.act.school_xx.dto.MarkDTO;
import com.act.school_xx.exceptions.EntityNotFoundException;
import com.act.school_xx.models.*;
import com.act.school_xx.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepository markRepository;
    private final CoursesRepository coursesRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Courses> getCourseById(Long id) {
        return coursesRepository.findById(id);
    }

    public List<MarkDTO> getMarksByTeacherId(Long teacherId) {
        List<Mark> marks = markRepository.findByTeacherId(teacherId);
        return marks.stream()
                .map(mark -> modelMapper.map(mark, MarkDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public MarkDTO addMark(MarkDTO markDTO) {
        Long teacherId = markDTO.getTeacherId();
        Long studentId = markDTO.getStudentId();
        Long courseId = markDTO.getCoursesId();

        if (teacherId == null || studentId == null || courseId == null) {
            throw new EntityNotFoundException("Teacher ID, Student ID, or Courses ID is null");
        }

        Teacher teacher = getTeacherById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with ID: " + teacherId));
        Student student = getStudentById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
        Courses course = getCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));

        Mark mark = new Mark();
        mark.setTeacher(teacher);
        mark.setStudent(student);
        mark.setCourses(course);
        mark.setMarks(markDTO.getMark().getMarks());
        Mark savedMark = markRepository.save(mark);

        return modelMapper.map(savedMark, MarkDTO.class);
    }

    public List<MarkDTO> getAllMarksWithDetails() {
        List<Mark> marks = markRepository.findAll();
        List<MarkDTO> markDTOs = new ArrayList<>();

        for (Mark mark : marks) {
            MarkDTO markDTO = modelMapper.map(mark, MarkDTO.class);

            String coursesTitle = mark.getCourses().getCoursesTitle();
            String teacherFullName = mark.getTeacher().getUser().getFirstName() + " " + mark.getTeacher().getUser().getMiddleName();

            Long userId = mark.getStudent().getUser().getId();
            Optional<User> userOptional = userRepository.findById(userId);
            String studentFullName = userOptional.map(user -> user.getFirstName() + " " + user.getLastName()).orElse("Unknown");

            markDTO.setStudentName(studentFullName);
            markDTO.setCourseTitle(coursesTitle);
            markDTO.setTeacherName(teacherFullName);

            CoursesDTO coursesDTO = modelMapper.map(mark.getCourses(), CoursesDTO.class);
            markDTO.setCoursesDTO((List<CoursesDTO>) coursesDTO);
            markDTOs.add(markDTO);
        }

        return markDTOs;
    }

}
