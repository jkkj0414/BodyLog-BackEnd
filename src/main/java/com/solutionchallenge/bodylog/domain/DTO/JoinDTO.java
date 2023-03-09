package com.solutionchallenge.bodylog.domain.DTO;

import com.solutionchallenge.bodylog.domain.Member;
import com.solutionchallenge.bodylog.domain.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JoinDTO {
    private String userId;
    private String userPassword;
    private String repeatedPassword;


    private List<MealDTO> mealDTOS;
    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .userPassword(userPassword)
                .role(Role.USER)
                .build();
    }

}
