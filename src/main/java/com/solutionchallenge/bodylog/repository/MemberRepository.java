package com.solutionchallenge.bodylog.repository;

import com.solutionchallenge.bodylog.domain.Meal;
import com.solutionchallenge.bodylog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);
}


