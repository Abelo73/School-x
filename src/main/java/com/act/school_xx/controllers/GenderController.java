package com.act.school_xx.controllers;

import com.act.school_xx.models.Gender;
import com.act.school_xx.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gender")
public class GenderController {

    private final GenderService genderService;
    @PostMapping("/addGender")
    public ResponseEntity<Gender> addGender(@RequestBody Gender gender){
        Gender savedGender = genderService.addGender(gender);
        return new ResponseEntity<>(savedGender, HttpStatus.CREATED);
    }
    @GetMapping("/getAllGenders")
    public ResponseEntity<List<Gender>> getAllGenders(){
        List<Gender> genderList = genderService.getAllGenders();
        return new ResponseEntity<>(genderList, HttpStatus.OK);
    }
}
