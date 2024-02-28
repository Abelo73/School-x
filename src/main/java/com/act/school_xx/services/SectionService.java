package com.act.school_xx.services;

import com.act.school_xx.exceptions.SectionNotFoundException;
import com.act.school_xx.models.Section;

import com.act.school_xx.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public Section getSectionById(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new SectionNotFoundException("Section with ID " + id + " not found"));
    }

    public Section createSection(Section section) {
        return sectionRepository.save(section);
    }

    public Section updateSection(Long id, Section section) {
        if (!sectionRepository.existsById(id)) {
            throw new SectionNotFoundException("Section with ID " + id + " not found");
        }
        section.setId(id);
        return sectionRepository.save(section);
    }

    public void deleteSection(Long id) {
        if (!sectionRepository.existsById(id)) {
            throw new SectionNotFoundException("Section with ID " + id + " not found");
        }
        sectionRepository.deleteById(id);
    }
}
