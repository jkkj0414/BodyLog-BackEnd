package com.solutionchallenge.bodylog.service;

import com.solutionchallenge.bodylog.domain.DTO.FindMealDTO;
import com.solutionchallenge.bodylog.domain.DTO.MealDTO;
import com.solutionchallenge.bodylog.domain.Meal;
import com.solutionchallenge.bodylog.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Slf4j
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;

    // 수정
    @Transactional
    public MealDTO updateByMeal(Long id, MealDTO mealDTO){
        Meal meal = loadMealById(id);
        meal.update(mealDTO);
        return mealRepository.saveAndFlush(meal).toDTO();
    }

    // 삭제
    @Transactional
    public void deleteById(Long id) {
        Meal meal = loadMealById(id);
        mealRepository.delete(meal);
    }

    public Meal loadMealById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 Meal이 존재하지 않습니다."));
    }

    // 식사 하나 조회
    @Transactional(readOnly = true)
    public MealDTO findByMeal(Long id) {
        Meal meal = mealRepository.findById(id).get();
        return   MealDTO.builder()
                .mealId(meal.getId())
                .type(meal.getType())
                .quantity(meal.getQuantity())
                .selectedDate(meal.getSelectedDate())
                .build();
    }
}
