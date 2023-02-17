package com.solutionchallenge.bodylog.repository;

import com.solutionchallenge.bodylog.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
