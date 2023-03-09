package com.solutionchallenge.bodylog.controller;


import com.solutionchallenge.bodylog.domain.DTO.JoinDTO;
import com.solutionchallenge.bodylog.domain.DTO.MealDTO;
import com.solutionchallenge.bodylog.domain.Meal;
import com.solutionchallenge.bodylog.service.MealService;
import com.solutionchallenge.bodylog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MealController {
    private  final MealService mealService;
    private  final MemberService memberService;

    //member 식사 전체 조회
//    @GetMapping("member/{id}/meals")
//    public ResponseEntity<List<MealDTO>> findMealByMemberId(
//            @PathVariable("id") Long id) {
//        return ResponseEntity.ok(memberService.findMemberById(id));
//    }

    //member 식사 전체 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<JoinDTO> findAllByMemberId(
            Principal principal,
            @PathVariable("user_id") String userId) {

        JoinDTO member = memberService.findByUserMealId(principal,userId);
        List<MealDTO> responses = mealService.findAllByMemberId(principal, userId);
        member.setMealDTOS(responses);

        return ResponseEntity.ok(member);
    }
    //저장
    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addMeal(
            Principal principal,
            @PathVariable("userId") String userId,
            @RequestBody MealDTO request) {

        Meal response = mealService.addByMeal(principal, userId, request);
        return ResponseEntity.ok("meal save success");
    }
    //수정
    @PatchMapping("/{userId}/{mealId}/update")
    public ResponseEntity<String> updateByMeal(Principal principal,
                                               @PathVariable("userId") String userId,
                                               @PathVariable("mealId") Long mealId,
                                               @RequestBody MealDTO request) {

        MealDTO response = mealService.updateByMeal(principal,userId,mealId,request);
        return ResponseEntity.ok("meal update success");
    }
    //삭제
    @DeleteMapping("/{userId}/{mealId}/delete")
    public ResponseEntity<String> deleteById(Principal principal,
                                             @PathVariable("userId") String userId,
                                             @PathVariable("mealId") Long mealId) {
        mealService.deleteById(principal, userId, mealId);
        return ResponseEntity.ok("meal delete success");
    }

//    // 하나만 조회
//    @GetMapping("/meals/{id}")
//    public ResponseEntity<?> findByMeal(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(mealService.findByMeal(id));
//    }
}
