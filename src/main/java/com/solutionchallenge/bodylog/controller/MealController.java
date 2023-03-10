package com.solutionchallenge.bodylog.controller;


import com.solutionchallenge.bodylog.domain.DTO.MealDTO;
import com.solutionchallenge.bodylog.domain.Meal;
import com.solutionchallenge.bodylog.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MealController {
    private  final MealService mealService;

    //자신의 meal 식사 하나 조회
    //member/{Id}/meals -> 여기서 {Id}는 mealId
    @GetMapping("/member/{Id}/meals")
    public ResponseEntity<MealDTO> findOneMeal(Principal principal, @PathVariable Long Id) {

        MealDTO mealDTO = mealService.findById(Id);
        if(principal.getName().equals(mealDTO.getUserId())) {
            return ResponseEntity.ok(mealDTO);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    //저장
    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addMeal(
            Principal principal, @PathVariable("userId") String userId,
            @RequestBody MealDTO request) {

        Meal response = mealService.addByMeal(principal, userId, request);
        return ResponseEntity.ok("meal save success");
    }


    //수정
    @PatchMapping("/{userId}/{mealId}/update")
    public ResponseEntity<String> updateByMeal(Principal principal,@PathVariable("userId") String userId,
                                               @PathVariable("mealId") Long mealId,
                                               @RequestBody MealDTO request) {

        MealDTO response = mealService.updateByMeal(principal,userId,mealId,request);
        return ResponseEntity.ok("meal update success");
    }
    //삭제
    @DeleteMapping("/{userId}/{mealId}/delete")
    public ResponseEntity<String> deleteById(Principal principal,@PathVariable("userId") String userId,
                                             @PathVariable("mealId") Long mealId) {
        mealService.deleteById(principal, userId, mealId);
        return ResponseEntity.ok("meal delete success");
    }

    //자신의 Meal 전제조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<MealDTO>> findAll(Principal principal,
                                                 @PathVariable("userId") String userId) {

        List<MealDTO> responses = mealService.findAllByMemberId(principal,userId);
        return ResponseEntity.ok(responses);
    }

}
