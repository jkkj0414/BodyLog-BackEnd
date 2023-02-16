package com.solutionchallenge.bodylog.domain;


import com.solutionchallenge.bodylog.domain.DTO.MealDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "meal")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Meal extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "quantitiy", nullable = false)
    private String quantitiy;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

//    public MealDTO toDTO(){
//        return MealDTO.builder()
//                .type(type)
//                .quantity(quantitiy)
//                .build();
//    }
}
