package com.act.school_xx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String refreshToken;
    private String accessToken;
    private String token;
    private String status;
    private String message;
}
