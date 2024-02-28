package com.act.school_xx.services;

import com.act.school_xx.models.EducationalLevel;
import com.act.school_xx.repository.EducationalLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationalLevelService {

    private final EducationalLevelRepository educationalLevelRepository;

    public EducationalLevel createEducationalLevel(EducationalLevel educationalLevel) {
        return educationalLevelRepository.save(educationalLevel);
    }

    public List<EducationalLevel> getAllEducationalLevels() {
        return educationalLevelRepository.findAll();
    }

    public EducationalLevel getEducationalLevelById(Long id) {
        return educationalLevelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Educational level not found with id: " + id));
    }

    public EducationalLevel updateEducationalLevel(Long id, EducationalLevel educationalLevel) {
        educationalLevel.setId(id);
        return educationalLevelRepository.save(educationalLevel);
    }

    public void deleteEducationalLevel(Long id) {
        educationalLevelRepository.deleteById(id);
    }
}
