package com.act.school_xx.controllers;

import com.act.school_xx.dto.AuthenticationResponse;
import com.act.school_xx.dto.RefreshTokenRequest;
import com.act.school_xx.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RefreshTokenController {

    private final AuthenticationService authService;

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthenticationResponse response = authService.refreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello from Refresh Token";
    }

}
