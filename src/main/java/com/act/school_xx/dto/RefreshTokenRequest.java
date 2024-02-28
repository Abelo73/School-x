package com.act.school_xx.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RefreshTokenRequest {
    private String refreshToken;
}