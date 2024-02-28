package com.act.school_xx.services;

import com.act.school_xx.exceptions.FieldOfStudyNotFoundException;
import com.act.school_xx.models.FieldOfStudy;

import com.act.school_xx.repository.FieldOfStudyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldOfStudyService {

    private final FieldOfStudyRepository fieldOfStudyRepository;

    public FieldOfStudyService(FieldOfStudyRepository fieldOfStudyRepository) {
        this.fieldOfStudyRepository = fieldOfStudyRepository;
    }

    public List<FieldOfStudy> getAllFieldsOfStudy() {
        return fieldOfStudyRepository.findAll();
    }

    public FieldOfStudy getFieldOfStudyById(Long id) {
        return fieldOfStudyRepository.findById(id)
                .orElseThrow(() -> new FieldOfStudyNotFoundException("Field of study not found with id: " + id));
    }

    public FieldOfStudy addFieldOfStudy(FieldOfStudy fieldOfStudy) {
        return fieldOfStudyRepository.save(fieldOfStudy);
    }

    public FieldOfStudy createFieldOfStudy(FieldOfStudy fieldOfStudy) {
        return fieldOfStudyRepository.save(fieldOfStudy);

    }

    public List<FieldOfStudy> getAllFieldOfStudies() {
        return fieldOfStudyRepository.findAll();

    }

    public void deleteFieldOfStudyById(Long id) {
        FieldOfStudy fieldOfStudy = getFieldOfStudyById(id);
        fieldOfStudyRepository.delete(fieldOfStudy);
    }

//    public FieldOfStudy updateFieldOfStudy(Long id, FieldOfStudy updatedFieldOfStudy) {
//        FieldOfStudy existingFieldOfStudy = getFieldOfStudyById(id);
//        existingFieldOfStudy.setFieldName(updatedFieldOfStudy.getFieldName());
//        return fieldOfStudyRepository.save(existingFieldOfStudy);
//    }
////
////    public void deleteFieldOfStudy(Long id) {
////        fieldOfStudyRepository.deleteById(id);
////    }
}
