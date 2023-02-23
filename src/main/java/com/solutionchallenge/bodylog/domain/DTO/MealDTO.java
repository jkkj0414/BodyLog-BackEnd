package com.solutionchallenge.bodylog.domain.DTO;


import com.solutionchallenge.bodylog.domain.Quantity;
import com.solutionchallenge.bodylog.domain.Type;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MealDTO {
    private Long mealId;
    private Type type;
    private Quantity quantity;
    private List<MemberDTO> MemberDTO;
    private LocalDateTime createdDate;;
    private LocalDateTime modifiedDate;

}
