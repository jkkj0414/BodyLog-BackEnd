package com.solutionchallenge.bodylog.service;

import com.solutionchallenge.bodylog.domain.DTO.JoinDTO;
import com.solutionchallenge.bodylog.domain.DTO.LoginDTO;
import com.solutionchallenge.bodylog.domain.DTO.MemberDTO;
import com.solutionchallenge.bodylog.domain.DTO.TokenDTO;
import com.solutionchallenge.bodylog.repository.MemberRepository;
import com.solutionchallenge.bodylog.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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

}
