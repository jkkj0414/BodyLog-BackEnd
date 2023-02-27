package com.solutionchallenge.bodylog.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class LoginResponseDTO {
    Long id;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpirationTime;
}