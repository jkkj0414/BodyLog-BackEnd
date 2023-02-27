package com.solutionchallenge.bodylog.service;

import com.solutionchallenge.bodylog.domain.DTO.*;
import com.solutionchallenge.bodylog.domain.Meal;
import com.solutionchallenge.bodylog.domain.Member;
import com.solutionchallenge.bodylog.repository.MealRepository;
import com.solutionchallenge.bodylog.repository.MemberRepository;
import com.solutionchallenge.bodylog.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MealRepository mealRepository;
    private final RedisTemplate redisTemplate;

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
        // 1. ID/PW 를 기반으로 Authentication 객체 생성
        // authentication 객체는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserId(), loginRequestDTO.getUserPassword());

        //2. 실제 검증(사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 JwtUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증된 정보를 기반으로 JWT 토큰 생성
        TokenDTO tokenDTO = tokenProvider.createToken(authentication);
        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenDTO.getRefreshToken(), tokenDTO.getRefreshTokenExpirationTime().getTime(), TimeUnit.MILLISECONDS);

        // 4. 로그인 성공하면 토큰DTO에 제대로 들어감
        return tokenDTO;
    }
    @Transactional
    public List<MealDTO> findMemberById(Long id) {
        List<Meal> findMeals = mealRepository.findMealByMemberId(id);
        return findMeals.stream().map(Meal::toDTO)
             .collect(Collectors.toList());
    }
    public ResponseEntity<?> logout(LogoutDTO logoutDTO) {
        log.info("로그아웃 로직");
        //accessToken 검증
        if (!tokenProvider.validateToken(logoutDTO.getAccessToken())) {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
        //Access Token에서 authentication 가져온다.
        Authentication authentication = tokenProvider.getAuthentication(logoutDTO.getAccessToken());
        //Redis에서 해당 authentication으로 저장된 refresh token이 있을 경우 삭제한다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName())!= null) {
            redisTemplate.delete("RT:" + authentication.getName());
        }
        //해당 AccessToken 유효시간 가지고 와서 BlackList로 저장하기
        Long expiration = tokenProvider.getExpiration(logoutDTO.getAccessToken());
        redisTemplate.opsForValue().set(logoutDTO.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    public ResponseEntity<?> reissue(TokenDTO tokenDTO) {
        if (!tokenProvider.validateToken(tokenDTO.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 정보가 유효하지 않습니다.");
        }
        Authentication authentication = tokenProvider.getAuthentication(tokenDTO.getAccessToken());
        //Redis에서 아이디 기반으로 저장된 refresh token 값을 가져온다.
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());
        if (!refreshToken.equals(tokenDTO.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 정보가 일치하지 않습니다.");
        }
        //로그아웃되어 Redis에 refresh 토큰이 없을 경우
        if (ObjectUtils.isEmpty(refreshToken)) {
            throw new RuntimeException("로그아웃 상태입니다.");
        }
        //새로운 토큰 생성
        TokenDTO newToken = tokenProvider.createToken(authentication);
        //refreshToken Redis 업데이트
        redisTemplate.opsForValue().set("RT:" + authentication.getName(), newToken.getRefreshToken(), newToken.getRefreshTokenExpirationTime().getTime(), TimeUnit.MILLISECONDS);

        return ResponseEntity.ok("토큰 정보 갱신");
    }

    public Long findId(LoginDTO loginDTO) {
        Member findMember = memberRepository.findByUserId(loginDTO.getUserId()).get();
        return findMember.getId();
    }

}
