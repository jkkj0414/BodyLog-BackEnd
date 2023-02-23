package com.solutionchallenge.bodylog.domain.DTO;


import com.solutionchallenge.bodylog.domain.Member;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
public class MemberDTO {
    private String userid;
    private String password;
    private List<MealDTO> meal;


    private LocalDateTime createdDate;;
    private LocalDateTime modifiedDate;

}

