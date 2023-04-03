package com.solutionchallenge.bodylog.domain.DTO;

import com.solutionchallenge.bodylog.domain.Member;
import com.solutionchallenge.bodylog.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor  // default constructor 추가
@AllArgsConstructor // 모든 매개변수를 가지는 생성자 추가
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
