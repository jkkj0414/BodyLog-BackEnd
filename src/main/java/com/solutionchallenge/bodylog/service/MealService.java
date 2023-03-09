package com.solutionchallenge.bodylog.service;

import com.solutionchallenge.bodylog.domain.DTO.MealDTO;
import com.solutionchallenge.bodylog.domain.Meal;
import com.solutionchallenge.bodylog.domain.Member;
import com.solutionchallenge.bodylog.repository.MealRepository;
import com.solutionchallenge.bodylog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final MemberRepository memberRepository;


    //accessToken의 사용자와 {userId}와 같다면 meal 저장 가능
    @Transactional
    public Meal addByMeal(Principal principal, String user_Id, MealDTO mealDTO) {

        if (!user_Id.equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "자신의 것만 등록이 가능합니다.");
        Member member = findEntityByMemberId(user_Id);

        Meal meal = Meal.builder()
                .type(mealDTO.getType())
                .quantity(mealDTO.getQuantity())
                .member(member) // 어떤 member가 가진 Meal인지
                .selectedDate(mealDTO.getSelectedDate())
                .build();
        mealRepository.save(meal);
        return meal;
    }

    // accessToken의 사용자와 {userId}와 같다면 meal 수정 가능
    @Transactional
    public MealDTO updateByMeal(Principal principal, String user_id,
                                Long id, MealDTO dto) {

        Meal meal = findEntityById(id);

        if (!user_id.equals(meal.getMember().getUserId()))

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    user_id + "의 meal을 찾을 수 없습니다.");

        if (!user_id.equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "자신의 meal만 수정 가능합니다.");

        meal.update(dto);
        return mealRepository.saveAndFlush(meal).toDTO();
    }

    // 삭제
    @Transactional
    public void deleteById(Principal principal, String user_id,
                           Long id) {
        Meal meal = findEntityById(id);
        if (!user_id.equals(meal.getMember().getUserId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    user_id + "는 이 meal을 가지고 있지 않습니다.");
        if (!user_id.equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "" +
                    "자신의 meal만 삭제 할 수 있습니다.");

        mealRepository.delete(meal);
    }

    // 식사 하나 조회
//    @Transactional(readOnly = true)
//    public MealDTO findByMeal(Long id) {
//        Meal meal = mealRepository.findById(id).get();
//        return MealDTO.builder()
//                .mealId(meal.getId())
//                .type(meal.getType())
//                .quantity(meal.getQuantity())
//                .selectedDate(meal.getSelectedDate())
//                .build();
//    }

    //meal_id 찾기
    public Meal findEntityById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 Meal이 존재하지 않습니다."));
    }

    //해당 사용자 찾을 수 없음
    public Member findEntityByMemberId(String user_id){
        return memberRepository.findByUserId(user_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "해당 사용자를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<MealDTO> findAllByMemberId(Principal principal, String user_id){
        if(!user_id.equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"자신만 볼 수 있습니다.");
        Member member = findEntityByMemberId(user_id);

        List<Meal> meals = mealRepository.findAllByMember(member);

        return meals.stream()
                .map(Meal::toDTO)
                .collect(Collectors.toList());

    }
}