package com.solutionchallenge.bodylog.domain.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.solutionchallenge.bodylog.domain.Quantity;
import com.solutionchallenge.bodylog.domain.Type;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MealDTO {
    private Long mealId;
    private Type type;
    private Quantity quantity;
    @JsonFormat (
            shape = JsonFormat.Shape.STRING,
            pattern = "MM-dd-yyyy",
            locale = "Asia/Seoul"
    )
    private LocalDate selectedDate;
    private String userId;

}
