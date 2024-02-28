package com.act.school_xx.dto;

import com.act.school_xx.models.Role;
import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Role role;
    private List<MarkDTO> markDTOS;
    private List<CoursesDTO> coursesDTOS;

    public StudentDTO(Long id, String firstName, String middleName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public StudentDTO() {

    }

//    public StudentDTO(Long id, String firstName, String middleName, String lastName, Role role) {
//
//    }


    // Setter method for a list of marks represented as integers
    public void setMarkDTOS(List<MarkDetail> marks) {
        // Convert list of integers to a list of MarkDTO objects
        // Implement this conversion logic based on your requirements
    }

    // Setter method for a single mark represented as a MarkDTO object
    public void setMarkDTO(MarkDTO markDTO) {
        this.markDTOS = List.of(markDTO);
    }

    // Setter method for a list of marks represented as MarkDetail objects
    public void setMarkDetails(List<MarkDetail> markDetails) {
        // Convert list of MarkDetail objects to a list of MarkDTO objects
        // Implement this conversion logic based on your requirements
    }
}
