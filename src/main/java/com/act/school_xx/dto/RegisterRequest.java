package com.act.school_xx.dto;

import com.act.school_xx.models.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String Email;
    private String mobile;
    private Gender gender;
    private String password;
    private Integer age;
    private String registrationDate;
    private Role role;


}
