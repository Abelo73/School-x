package com.act.school_xx.controllers;

import com.act.school_xx.models.User;
import com.act.school_xx.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final AuthenticationService authenticationService;

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello, Its working";
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = authenticationService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
