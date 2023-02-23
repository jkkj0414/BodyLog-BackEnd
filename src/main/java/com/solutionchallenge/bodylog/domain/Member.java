package com.solutionchallenge.bodylog.domain;

import com.solutionchallenge.bodylog.domain.DTO.JoinDTO;
import com.solutionchallenge.bodylog.domain.DTO.MemberDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Meal> meals =new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private Role role;

    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .userid(userId)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();
    }
}
