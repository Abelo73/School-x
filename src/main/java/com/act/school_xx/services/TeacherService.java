package com.act.school_xx.services;

import com.act.school_xx.dto.*;
import com.act.school_xx.models.*;
import com.act.school_xx.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {


    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final EducationalLevelRepository educationalLevelRepository;
    private final SectionRepository sectionRepository;
    private final SemesterRepository semesterRepository;
    private final CoursesRepository coursesRepository;
    private final GradeRepository gradeRepository;
    private final MarkRepository markRepository;

//    public void registerAsTeacher(Long userId, TeacherRegistrationRequest request) {
//        // Fetch the user
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
//
//        // Check if the user is already registered as a teacher
//        if (user.getTeacher() == null) {
//            Teacher teacher = new Teacher();
//            teacher.setUser(user);
//            teacher.setSalary(request.getSalary().toString());
//
//            // Fetch and set other entities
//            teacher.setFieldOfStudy(fetchFieldOfStudy(request.getFieldOfStudyId()));
//            teacher.setEducationalLevel(fetchEducationalLevel(request.getEducationalLevelId()));
//
//            // Fetch and set the Courses entities based on provided courseIds
//            List<Courses> courses = fetchCourses(request.getCourseIds());
//            teacher.setCourses(courses);
//
//            List<Semester> semesters = fetchSemester(request.getSemesterIds());
//            teacher.setSemesters(semesters);
//
//            List<Grade> grades = fetchGrades(request.getGradeIds());
//            teacher.setGrade(grades);
//
//            if (request.getSectionIds() != null) {
//                List<Section> sections = fetchSection(request.getSectionIds());
//                teacher.setSections(sections);
//            }
//
//            teacherRepository.save(teacher);
//
//            // Update the role of the user to TEACHER
//            user.setRole(Role.TEACHER);
//            userRepository.save(user);
//        } else {
//            throw new RuntimeException("User is already registered as a teacher.");
//        }
//    }

    public void registerAsTeacher(Long userId, TeacherRegistrationRequest request) {
        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Check if the user's role is USER
        if (user.getRole() == Role.USER) {
            Teacher teacher = new Teacher();
            teacher.setUser(user);
            teacher.setSalary(request.getSalary().toString());

            // Fetch and set other entities
            teacher.setFieldOfStudy(fetchFieldOfStudy(request.getFieldOfStudyId()));
            teacher.setEducationalLevel(fetchEducationalLevel(request.getEducationalLevelId()));

            // Fetch and set the Courses entities based on provided courseIds
            List<Courses> courses = fetchCourses(request.getCourseIds());
            teacher.setCourses(courses);

            List<Semester> semesters = fetchSemester(request.getSemesterIds());
            teacher.setSemesters(semesters);

            List<Grade> grades = fetchGrades(request.getGradeIds());
            teacher.setGrade(grades);

            if (request.getSectionIds() != null) {
                List<Section> sections = fetchSection(request.getSectionIds());
                teacher.setSections(sections);
            }

            teacherRepository.save(teacher);

            // Update the role of the user to TEACHER
            user.setRole(Role.TEACHER);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User is already registered as a teacher or has a different role.");
        }
    }


    public List<DetailedTeacherDTO> getAllTeachers() {
        List<User> teachers = userRepository.findByRole(Role.TEACHER);
        return teachers.stream()
                .map(user -> new DetailedTeacherDTO(user.getTeacher()))
                .collect(Collectors.toList());
    }


//    public List<DetailedTeacherDTO> getAllTeachers() {
//        List<Teacher> teachers = teacherRepository.findAll();
//        return teachers.stream()
//                .map(DetailedTeacherDTO::new)
//                .collect(Collectors.toList());
//    }
//
//
    public TeacherDTO getTeacherDetails(Long teacherId) {
    System.out.println("Fetching teacher details for ID: " + teacherId);
    Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
    if (teacher == null) {
        System.out.println("Teacher not found with ID: " + teacherId);
        return null;
    }
    System.out.println("Teacher found with ID: " + teacherId);

    TeacherDTO teacherDTO = new TeacherDTO();
    teacherDTO.setId(teacher.getId());
    teacherDTO.setFirstName(teacher.getUser().getFirstName());
    System.out.println("Teacher name: " + teacherDTO.getFirstName());

    List<CoursesDTOT> coursesDTOTS = new ArrayList<>();
    for (Courses course : teacher.getCourses()) {
        System.out.println("Fetching details for course ID: " + course.getId());
        CoursesDTOT coursesDTOT = new CoursesDTOT();
        coursesDTOT.setId(course.getId());
        coursesDTOT.setCoursesTitle(course.getCoursesTitle());
        System.out.println("Course title: " + coursesDTOT.getCoursesTitle());

        List<StudentDTO> studentDTOs = new ArrayList<>();
        // Fetch marks for the current course and teacher combination
        List<Mark> marks = (List<Mark>) markRepository.findByCoursesAndTeacher(course, teacher);
        System.out.println("Marks for course " + course.getId() + " and teacher " + teacher.getId() + ": " + marks.size());
        for (Mark mark : marks) {
            System.out.println("Fetching student details for mark ID: " + mark.getId());
            StudentDTO studentDTO = new StudentDTO();
            Student student = mark.getStudent();
            if (student != null) {
                User studentUser = student.getUser();
                if (studentUser != null) {
                    studentDTO.setId(student.getId());
                    studentDTO.setFirstName(studentUser.getFirstName());
                    studentDTO.setMiddleName(studentUser.getMiddleName());
                    System.out.println("Student details for mark " + mark.getId() + ": " + studentUser.getFirstName() + " " + studentUser.getLastName());
                } else {
                    System.err.println("User details not found for student " + student.getId());
                }
            } else {
                System.err.println("Student not found for mark " + mark.getId());
            }
            studentDTOs.add(studentDTO);
        }
        coursesDTOT.setStudents(studentDTOs);
        coursesDTOTS.add(coursesDTOT);
    }
    teacherDTO.setCourses(coursesDTOTS);
    return teacherDTO;
}



    public List<DetailedTeacherDTO> getAllTeachersWithStudents() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .map(this::mapToDetailedTeacherDTO)
                .collect(Collectors.toList());
    }

    private DetailedTeacherDTO mapToDetailedTeacherDTO(Teacher teacher) {
        DetailedTeacherDTO detailedTeacherDTO = new DetailedTeacherDTO();
        detailedTeacherDTO.setId(teacher.getId());
        detailedTeacherDTO.setFirstName(teacher.getUser().getFirstName());
        detailedTeacherDTO.setMiddleName(teacher.getUser().getMiddleName());
        detailedTeacherDTO.setLastName(teacher.getUser().getLastName());
        detailedTeacherDTO.setEmail(teacher.getUser().getEmail());
        detailedTeacherDTO.setMobile(teacher.getUser().getMobile());
        detailedTeacherDTO.setAge(teacher.getUser().getAge());
        detailedTeacherDTO.setRole(Role.valueOf(teacher.getUser().getRole().toString()));
        detailedTeacherDTO.setSalary(teacher.getSalary());
        List<CoursesDTO> coursesDTOList = new ArrayList<>();
        for (Courses course : teacher.getCourses()) {
            CoursesDTO coursesDTO = new CoursesDTO();
            coursesDTO.setId(course.getId());
            coursesDTO.setCoursesTitle(course.getCoursesTitle());
            List<StudentDTO> studentDTOList = new ArrayList<>();
            for (Student student : course.getStudents()) {
                StudentDTO studentDTO = new StudentDTO();
                studentDTO.setId(student.getId());
                studentDTO.setFirstName(student.getUser().getFirstName());
                studentDTO.setMiddleName(student.getUser().getMiddleName());
                studentDTO.setLastName(student.getUser().getLastName());
                // Add more student details as needed
                studentDTOList.add(studentDTO);
            }
            coursesDTO.setStudents(studentDTOList);
            coursesDTOList.add(coursesDTO);
        }
        detailedTeacherDTO.setCourses(coursesDTOList);
        return detailedTeacherDTO;
    }


    private FieldOfStudy fetchFieldOfStudy(Long fieldOfStudyId) {
        return fieldOfStudyRepository.findById(fieldOfStudyId)
                .orElseThrow(() -> new EntityNotFoundException("FieldOfStudy not found with ID: " + fieldOfStudyId));
    }

    private EducationalLevel fetchEducationalLevel(Long educationalLevelId) {
        return educationalLevelRepository.findById(educationalLevelId)
                .orElseThrow(() -> new EntityNotFoundException("EducationalLevel not found with ID: " + educationalLevelId));
    }


    private  List<Semester> fetchSemester(List<Long> semesterIds){
        return semesterIds.stream()
                .map(semesterId -> semesterRepository.findById(semesterId)
                        .orElseThrow(()->new EntityNotFoundException("Semester Not found with ID: "+semesterId)))
                .collect(Collectors.toList());

    }

    private List<Courses> fetchCourses(List<Long> courseIds) {
        return courseIds.stream()
                .map(courseId -> coursesRepository.findById(courseId)
                        .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId)))
                .collect(Collectors.toList());
    }

    private List<Grade> fetchGrades(List<Long> gradeIds) {
        return gradeIds.stream()
                .map(gradeId -> gradeRepository.findById(gradeId)
                        .orElseThrow(() -> new EntityNotFoundException("Grade not found with ID: " + gradeId)))
                .collect(Collectors.toList());
    }

    private List<Section> fetchSection(List<Long> sectionIds){
        return sectionIds.stream()
                .map(sectionId->sectionRepository.findById(sectionId)
                        .orElseThrow(()->new EntityNotFoundException("Section not found with ID: "+sectionId)))
                .collect(Collectors.toList());
    }

    public boolean isValidTeacher(Long teacherId) {
        return teacherRepository.existsById(teacherId);
    }


    public Optional<Teacher> getTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId);
    }


}
