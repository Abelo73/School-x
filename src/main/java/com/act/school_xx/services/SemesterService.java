package com.act.school_xx.services;

import com.act.school_xx.exceptions.SemesterNotFoundException;
import com.act.school_xx.models.Semester;
import com.act.school_xx.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public Semester addSemester(Semester semester) {
        return semesterRepository.save(semester);
    }

    public Semester updateSemester(Long id, Semester updatedSemester) {
        Optional<Semester> existingSemesterOptional = semesterRepository.findById(id);
        if (existingSemesterOptional.isPresent()) {
            updatedSemester.setId(id);
            return semesterRepository.save(updatedSemester);
        } else {
            throw new SemesterNotFoundException("Semester with id " + id + " not found");
        }
    }

    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll();
    }

    public Semester getSemesterById(Long id) {
        return semesterRepository.findById(id)
                .orElseThrow(() -> new SemesterNotFoundException("Semester with id " + id + " not found"));
    }

    public void deleteSemester(Long id) {
        semesterRepository.deleteById(id);
    }
}
