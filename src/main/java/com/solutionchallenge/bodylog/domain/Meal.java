package com.solutionchallenge.bodylog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solutionchallenge.bodylog.domain.DTO.MealDTO;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "meal")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Meal{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Quantity quantity;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy", timezone = "Asia/Seoul")
    private LocalDate selectedDate;

    public MealDTO toDTO(){
        return MealDTO.builder()
                .mealId(id)
                .type(type)
                .quantity(quantity)
                .selectedDate(selectedDate)
                .userId(getMember().getUserId())
                .build();
    }
    public void update(MealDTO mealDTO) {
        this.type= mealDTO.getType();
        this.quantity=mealDTO.getQuantity();
        this.selectedDate=mealDTO.getSelectedDate();
    }
}
