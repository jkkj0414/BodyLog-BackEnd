package com.solutionchallenge.bodylog.domain.DTO;

import lombok.Data;

@Data
public class LogoutDTO {
    private String accessToken;
    private String refreshToken;
}
