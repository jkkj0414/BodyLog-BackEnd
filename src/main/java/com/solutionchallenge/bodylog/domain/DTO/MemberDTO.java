package com.solutionchallenge.bodylog.domain.DTO;


import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MemberDTO {
    private String userid;
    private String password;
    private List<MealDTO> meal;
}
