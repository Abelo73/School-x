package com.act.school_xx.services;

import com.act.school_xx.models.Gender;
import com.act.school_xx.repository.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenderService {

    private final GenderRepository genderRepository;
    public Gender addGender(Gender gender) {
        return genderRepository.save(gender);
    }

    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }
}
