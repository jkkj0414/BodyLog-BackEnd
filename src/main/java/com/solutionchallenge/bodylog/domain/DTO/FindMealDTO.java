package com.solutionchallenge.bodylog.domain.DTO;

import com.solutionchallenge.bodylog.domain.Quantity;
import com.solutionchallenge.bodylog.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindMealDTO {
    private Long mealId;
    private Type type;
    private Quantity quantity;
    private LocalDateTime createdDate;;
    private LocalDateTime modifiedDate;
}
