package com.solutionchallenge.bodylog.repository;


import com.solutionchallenge.bodylog.domain.Meal;
import com.solutionchallenge.bodylog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {

    Optional<Meal> findById(Long Id);

    List<Meal> findAllByMember(Member member);

    List<Meal> findBySelectedDateAndMember(LocalDate selectedDate, Member member);
}
