package com.solutionchallenge.bodylog.repository;

import com.solutionchallenge.bodylog.domain.Meal;
import com.solutionchallenge.bodylog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findMealByMemberId(Long memberId);
    List<Meal> findAllByMember(Member member);
}
