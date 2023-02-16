package com.solutionchallenge.bodylog.domain.DTO;

import com.solutionchallenge.bodylog.domain.Member;
import com.solutionchallenge.bodylog.domain.Role;
import lombok.Data;

@Data
public class JoinDTO {
    private String userId;
    private String userPassword;
    private String repeatedPassword;

    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .userPassword(userPassword)
                .role(Role.USER)
                .build();
    }
}
