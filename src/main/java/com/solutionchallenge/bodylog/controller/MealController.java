package com.solutionchallenge.bodylog.controller;


import com.solutionchallenge.bodylog.domain.Meal;
import com.solutionchallenge.bodylog.domain.Member;
import com.solutionchallenge.bodylog.domain.Quantity;
import com.solutionchallenge.bodylog.domain.Type;
import com.solutionchallenge.bodylog.repository.MealRepository;
import com.solutionchallenge.bodylog.repository.MemberRepository;
import com.solutionchallenge.bodylog.security.TokenProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class MealController {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final MealRepository mealRepository;
    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @PostMapping("/meals")
    @Transactional
    public ResponseEntity addMeal(HttpServletRequest request, @RequestBody TypeAndQuantityDto typeAndQuantityDto)  {
        // 액세스토큰 가져오기
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        // 액세스토큰으로 Authentication 객체 가져오기
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        // userId로 Member 객체 찾기
        Member member = memberRepository.findByUserId(authentication.getName()).get();

        // Member 가진 Meal List에 새로운 Meal을 추가해야 함
        Meal meal = Meal.builder()
                .type(typeAndQuantityDto.getType())
                .quantity(typeAndQuantityDto.getQuantity())
                .member(member) // 어떤 member가 가진 Meal인지
                .build();

        mealRepository.save(meal);
        return ResponseEntity.ok("ok");
    }

    @Getter
    static class TypeAndQuantityDto{
        private Type type;
        private Quantity quantity;
    }



}
