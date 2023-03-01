package com.solutionchallenge.bodylog.domain.DTO;


import com.solutionchallenge.bodylog.domain.Quantity;
import com.solutionchallenge.bodylog.domain.Type;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Getter
public class MealDTO {
    private Long mealId;
    private Type type;
    private Quantity quantity;
    private LocalDate selectedDate;

}
