package com.solutionchallenge.bodylog.controller;


import com.solutionchallenge.bodylog.domain.DTO.JoinDTO;
import com.solutionchallenge.bodylog.domain.DTO.MealDTO;
import com.solutionchallenge.bodylog.domain.DTO.MemberDTO;
import com.solutionchallenge.bodylog.service.MealService;
import com.solutionchallenge.bodylog.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


//@Slf4j
//@RestController
//@RequiredArgsConstructor
//public class MemberController {
//
//    private final MemberService memberService;
//    private final MealService mealService;
//
//    @GetMapping("/members")
//    public ResponseEntity<List<MemberDTO>> findAllMember() {
//        List<MemberDTO> responses = memberService.findAllMember();
//
//        if (responses.isEmpty()) {
//            return ResponseEntity
//                    .noContent()
//                    .build();
//        }
//
//        return ResponseEntity.ok(responses);
//    }
//
//}
