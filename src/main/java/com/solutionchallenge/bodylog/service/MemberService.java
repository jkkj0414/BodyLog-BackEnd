package com.solutionchallenge.bodylog.service;

import com.solutionchallenge.bodylog.domain.DTO.*;
import com.solutionchallenge.bodylog.domain.Meal;
import com.solutionchallenge.bodylog.domain.Member;
import com.solutionchallenge.bodylog.repository.MealRepository;
import com.solutionchallenge.bodylog.repository.MemberRepository;
import com.solutionchallenge.bodylog.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MealRepository mealRepository;

    @Transactional
    public void join(JoinDTO joinRequestDTO) {
        if(memberRepository.findByUserId(joinRequestDTO.getUserId()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        if(!joinRequestDTO.getUserPassword().equals(joinRequestDTO.getRepeatedPassword())) {
            throw new IllegalStateException("입력한 비밀번호가 일치하지 않습니다.");
        }
        joinRequestDTO.setUserPassword(passwordEncoder.encode(joinRequestDTO.getUserPassword()));
        memberRepository.save(joinRequestDTO.toEntity());
    }

    @Transactional
    public TokenDTO login(LoginDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserId(), loginRequestDTO.getUserPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.createToken(authentication);
    }
    @Transactional
    public List<MealDTO> findMemberById(Long id) {
        List<Meal> findMeals = mealRepository.findMealByMemberId(id);
        return findMeals.stream().map(Meal::toDTO)
             .collect(Collectors.toList());
    }
}
