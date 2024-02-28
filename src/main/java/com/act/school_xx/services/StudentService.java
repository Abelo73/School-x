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
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final SectionRepository sectionRepository;
    private final SemesterRepository semesterRepository;
    private final GradeRepository gradeRepository;
    private final CoursesRepository coursesRepository;
    private final TeacherRepository teacherRepository;

//    public void registerAsStudent(Long userId, StudentRegistrationRequest request) {
//        // Fetch the user
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
//
//        // Check if the user is already registered as a student
//        if (user.getStudent() == null) {
//            Student student = new Student();
//            student.setUser(user);
//
//            // Fetch and set other entities
//            student.setSection(fetchSection(request.getSectionId()));
//            student.setGrade(fetchGrade(request.getGradeId()));
////            student.setTeacher(fetchTeacher(request.getTeacherId()));
//
//
////            List<CoursesDTO> coursesDTOS = fetCourses(request.getCoursesId());
////            student.setCourses(coursesDTOS);
//
//            List<Courses> coursesDTOS = fetchCourses(request.getCoursesId());
//            student.setCourses(coursesDTOS); // <-- Issue might be here
//            List<Semester> semesters = fetchSemester(request.getSemesterId());
//            student.setSemesters(semesters);
//            studentRepository.save(student);
//
//            // Update the role of the user to STUDENT
//            user.setRole(Role.STUDENT);
//            userRepository.save(user);
//        } else {
//            throw new RuntimeException("User is already registered as a student.");
//        }
//    }

    public void registerAsStudent(Long userId, StudentRegistrationRequest request) {
        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Check if the user is already registered as a student
        if (user.getStudent() == null) {
            // Check if the user's role is USER
            if (user.getRole() == Role.USER) {
                Student student = new Student();
                student.setUser(user);

                // Fetch and set other entities
                student.setSection(fetchSection(request.getSectionId()));
                student.setGrade(fetchGrade(request.getGradeId()));

                List<Courses> coursesDTOS = fetchCourses(request.getCoursesId());
                student.setCourses(coursesDTOS);
                List<Semester> semesters = fetchSemester(request.getSemesterId());
                student.setSemesters(semesters);
                studentRepository.save(student);

                // Update the role of the user to STUDENT
                user.setRole(Role.STUDENT);
                userRepository.save(user);
            } else {
                throw new RuntimeException("User's role must be USER to register as a student.");
            }
        } else {
            throw new RuntimeException("User is already registered as a student.");
        }
    }


//    private Teacher fetchTeacher(Long teacherId) {
//        return teacherRepository.findById(teacherId)
//                .orElseThrow(()->new EntityNotFoundException("Teacher with Id ot found"));
//    }


    private Section fetchSection(Long sectionId) {
        return sectionRepository.findById(sectionId)
                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + sectionId));
    }

    private Grade fetchGrade(Long gradeId) {
        return gradeRepository.findById(gradeId)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found with ID: " + gradeId));
    }


    public List<StudentInfoDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::mapToStudentDTO)
                .collect(Collectors.toList());
    }

//    private List<CoursesDTO> fetCourses(List<Long> coursesId) {
//        List<Courses> courses = coursesRepository.findAll();
//        return courses.stream()
//                .map(this::mapToCourseDTO)
//                .collect(Collectors.toList());
//    }

    private List<Courses> fetchCourses(List<Long> coursesId) {
        List<Courses> courses = new ArrayList<>();
        for (Long courseId : coursesId) {
            Courses course = coursesRepository.findById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));
            courses.add(course);
        }
        return courses;
    }



    private StudentInfoDTO mapToStudentDTO(Student student) {
        StudentInfoDTO studentDTO = new StudentInfoDTO();
        // Map relevant fields from Student entity to StudentDTO
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getUser().getFirstName());
        studentDTO.setLastName(student.getUser().getLastName());
        studentDTO.setMiddleName(student.getUser().getMiddleName());
        studentDTO.setRole(student.getUser().getRole());
        studentDTO.setAge(student.getUser().getAge());
        studentDTO.setEmail(student.getUser().getEmail());
        studentDTO.setRegistrationDate(student.getUser().getRegistrationDate());
        studentDTO.setMobile(student.getUser().getMobile());
        studentDTO.setRole(student.getUser().getRole());
//        studentDTO.setGradeDTO(new GradeDTO());

        // Fetch and map grade
        Grade grade = student.getGrade();
        if (grade != null) {
            GradeDTO gradeDTO = new GradeDTO();
            gradeDTO.setId(grade.getId());
            gradeDTO.setGradeNumber(grade.getGradeNumber());
            studentDTO.setGradeDTO(gradeDTO);

            // Fetch and map courses based on grade
            List<Courses> courses = grade.getCourses();
            if (courses != null && !courses.isEmpty()) {
                List<CoursesDTO> courseDTOs = courses.stream()
                        .map(this::mapToCourseDTO)
                        .collect(Collectors.toList());
                studentDTO.setCourses(courseDTOs);
            }
        }

        // Fetch and map section
        Section section = student.getSection(); // Assuming there's a direct association between student and section
        if (section != null) {
            SectionDTO sectionDTO = new SectionDTO();
            sectionDTO.setId(section.getId());
            sectionDTO.setSectionName(section.getSectionName());
            // Set other section fields as needed
            studentDTO.setSectionDTO(sectionDTO);
        }
        // Map semesters to SemesterDTOs
        List<SemesterDTO> semesterDTOs = student.getSemesters().stream()
                .map(this::mapToSemesterDTO) // Mapping each semester to SemesterDTO
                .collect(Collectors.toList());
        studentDTO.setSemesterDTO(semesterDTOs);

        List<Courses> courses = student.getCourses(); // Assuming there's a direct association between student and courses
        if (courses != null && !courses.isEmpty()) {
            List<CoursesDTO> courseDTOs = courses.stream()
                    .map(this::mapToCourseDTO)
                    .collect(Collectors.toList());
            studentDTO.setCourses(courseDTOs);
        }
        return studentDTO;
    }

//    private CoursesDTO mapToCourseDTO(Courses courses) {
//        CoursesDTO courseDTO = new CoursesDTO();
//        // Map relevant fields from Course entity to CourseDTO
//        courseDTO.setId(courses.getId());
//        courseDTO.setCoursesTitle(courses.getCoursesTitle());
//        courseDTO.setFirstName(courses.getTeacher().getFirstName());
//
//        // Set other course fields as needed
//        return courseDTO;
//    }

    private CoursesDTO mapToCourseDTO(Courses courses) {
        CoursesDTO courseDTO = new CoursesDTO();
        // Map relevant fields from Course entity to CourseDTO
        courseDTO.setId(courses.getId());
        courseDTO.setCoursesTitle(courses.getCoursesTitle());


        // Fetch and set the teacher's first name
        Teacher teacher = (Teacher) courses.getTeacher();
        if (teacher != null) {
            courseDTO.setFirstName(teacher.getUser().getFirstName());
            courseDTO.setMiddleName(teacher.getUser().getMiddleName());
            courseDTO.setEducationalLevel(teacher.getEducationalLevel());
            courseDTO.setFieldOfStudy(teacher.getFieldOfStudy());
            courseDTO.setSalary(teacher.getSalary());
            courseDTO.setLastName(teacher.getUser().getLastName());
            courseDTO.setRole(teacher.getUser().getRole());
//            courseDTO.setSalary(teacher.getSalary());

        }

        // Set other course fields as needed
        return courseDTO;
    }



    private  List<Semester> fetchSemester(List<Long> semesterIds){
        return semesterIds.stream()
                .map(semesterId -> semesterRepository.findById(semesterId)
                        .orElseThrow(()->new EntityNotFoundException("Semester Not found with ID: "+semesterId)))
                .collect(Collectors.toList());

    }




    private SemesterDTO mapToSemesterDTO(Semester semester) {
        SemesterDTO semesterDTO = new SemesterDTO();
        semesterDTO.setId(semester.getId());
        semesterDTO.setSemesterName(semester.getSemesterName());
//        semesterDTO.setName(semester.getName());
        // Map other fields as needed
        return semesterDTO;
    }

    private StudentInfoDTO mapToStudentDTO(User user) {
        StudentInfoDTO studentDTO = new StudentInfoDTO();
        // Map relevant fields from User entity to StudentInfoDTO
        studentDTO.setId(user.getId());
        studentDTO.setFirstName(user.getFirstName());
        studentDTO.setLastName(user.getLastName());
        // Map other fields as needed
        return studentDTO;
    }

//    public List<StudentInfoDTO> searchStudents(String firstName, String lastName, String email, String mobile) {
//        // Search for users based on the provided parameters
//        List<User> users = userRepository.searchUsers(firstName, lastName, email, mobile);
//
//        // Map the found users to StudentInfoDTO
//        return users.stream()
//                .map(this::mapToStudentDTO)
//                .collect(Collectors.toList());
//    }

//    public List<StudentInfoDTO> searchStudents(String firstName, String lastName, String email, String mobile) {
//        // Delegate the search operation to the UserRepository
//        List<User> users = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileContainingIgnoreCase(
//                firstName, lastName, email, mobile);
//
//        // Map the found users to StudentInfoDTO
//        List<StudentInfoDTO> studentDTOs = users.stream()
//                .map(user -> mapToStudentDTO(user.getStudent())) // Assuming there's a direct association between User and Student
//                .collect(Collectors.toList());
//
//        return studentDTOs;
//    }

//    public List<StudentInfoDTO> searchStudents(String firstName, String lastName, String email, String mobile) {
//        // Delegate the search operation to the UserRepository
//        List<User> users = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileContainingIgnoreCase(
//                firstName, lastName, email, mobile);
//
//        // Map the found users to StudentInfoDTO
//        List<StudentInfoDTO> studentDTOs = new ArrayList<>();
//        for (User user : users) {
//            if (user.getStudent() != null) { // Check if the user has an associated student
//                StudentInfoDTO studentDTO = mapToStudentDTO(user.getStudent());
//                studentDTOs.add(studentDTO);
//            }
//        }
//
//        return studentDTOs;
//    }

    public List<StudentInfoDTO> searchStudents(String firstName, String lastName, String email, String mobile) {
        // Delegate the search operation to the UserRepository, filtering by role
        List<User> users = userRepository.findByRoleAndFirstNameContainingIgnoreCaseOrRoleAndLastNameContainingIgnoreCaseOrRoleAndEmailContainingIgnoreCaseOrRoleAndMobileContainingIgnoreCase(
                Role.STUDENT, firstName, Role.STUDENT, lastName, Role.STUDENT, email, Role.STUDENT, mobile);

        // Map the found users to StudentInfoDTO
        List<StudentInfoDTO> studentDTOs = new ArrayList<>();
        for (User user : users) {
            if (user.getStudent() != null) { // Check if the user has an associated student
                StudentInfoDTO studentDTO = mapToStudentDTO(user.getStudent());
                studentDTOs.add(studentDTO);
            }
        }



        return studentDTOs;
    }


    public boolean isValidStudent(Long studentId) {
        return studentRepository.existsById(studentId);
    }


    public Optional<Student> getStudentById(Long studentId) {

        return studentRepository.findById(studentId);
    }
}
