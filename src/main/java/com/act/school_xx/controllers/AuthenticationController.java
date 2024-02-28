package com.act.school_xx.controllers;

import com.act.school_xx.dto.AuthenticationResponse;
import com.act.school_xx.dto.RegisterRequest;
import com.act.school_xx.exceptions.UserNotFoundException;
import com.act.school_xx.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
//@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

//    @DeleteMapping("/user/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws UserNotFoundException {
//        authenticationService.deleteUser(id);
//        return ResponseEntity.ok("User deleted successfully");
//    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            authenticationService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with ID " + id + " not found");
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }
}
