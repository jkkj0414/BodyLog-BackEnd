package com.solutionchallenge.bodylog.controller;


import com.solutionchallenge.bodylog.domain.DTO.MealDTO;
import com.solutionchallenge.bodylog.domain.DTO.MemberDTO;
import com.solutionchallenge.bodylog.domain.Meal;
import com.solutionchallenge.bodylog.domain.Member;
import com.solutionchallenge.bodylog.domain.Quantity;
import com.solutionchallenge.bodylog.domain.Type;
import com.solutionchallenge.bodylog.repository.MealRepository;
import com.solutionchallenge.bodylog.repository.MemberRepository;
import com.solutionchallenge.bodylog.security.TokenProvider;
import com.solutionchallenge.bodylog.service.MealService;
import com.solutionchallenge.bodylog.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.DateType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MealController {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final MealRepository mealRepository;
    private  final MealService mealService;
    private  final MemberService memberService;

    @GetMapping("member/{id}/meals")
    public ResponseEntity<List<MealDTO>> findMealByMemberId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.findEntitiesById(id));
    }

    //저장
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

    //meal 전체조회
    @GetMapping("/main")
    public ResponseEntity<List<MealDTO>> findAll() {
        List<MealDTO> responses = mealService.findAll();

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(responses);
    }

        @GetMapping("/main/{id}")
        public ResponseEntity<List<MealDTO>> findAllMealId(@PathVariable("id") Long id) {
                List<MealDTO> responses = mealService.findAllByMeal(id);

                if (responses.isEmpty()) {
                        return ResponseEntity
                          .noContent()
                           .build();
        }
        return ResponseEntity.ok(responses);
    }

    //수정
    @PatchMapping("/meals/{id}")
    public ResponseEntity<MealDTO> updateByMeal(@PathVariable("id") Long id, @RequestBody MealDTO request) {
        MealDTO response = mealService.updateByMeal(id,request);
        return ResponseEntity.ok(response);
    }

    //삭제
    @DeleteMapping("/meals/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        mealService.deleteById(id);
        return ResponseEntity
                .ok(null);
    }

    // 하나만 조회
    @GetMapping("/meals/{id}")
    public ResponseEntity<?> findByLuckyBag(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mealService.findByMeal(id));
    }
}
