package com.solutionchallenge.bodylog.controller;

import com.solutionchallenge.bodylog.domain.DTO.LoginDTO;
import com.solutionchallenge.bodylog.domain.DTO.LoginResponseDTO;
import com.solutionchallenge.bodylog.domain.DTO.LogoutDTO;
import com.solutionchallenge.bodylog.domain.DTO.TokenDTO;
import com.solutionchallenge.bodylog.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final MemberService memberService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO loginRequestDTO) {
        TokenDTO tokenDTO = memberService.login(loginRequestDTO);
        Long id = memberService.findId(loginRequestDTO);

        return LoginResponseDTO.builder().memberId(id)
                .grantType(tokenDTO.getGrantType())
                .accessToken(tokenDTO.getAccessToken())
                .refreshToken(tokenDTO.getRefreshToken())
                .refreshTokenExpirationTime(tokenDTO.getRefreshTokenExpirationTime())
                .build();
    }
    @PostMapping("/log-out")
    public ResponseEntity<?> logout(@RequestBody LogoutDTO logoutDTO) {
        log.info("로그아웃");
        return memberService.logout(logoutDTO);
    }

    //access 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenDTO tokenDTO) {
        log.info("토큰 재발급");
        return memberService.reissue(tokenDTO);
    }
}
