package com.solutionchallenge.bodylog.domain.DTO;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MealDTO {
    private Long mealId;
    private String type;
    private String quantity;
    private List<MemberDTO> MemberDTO;
}
