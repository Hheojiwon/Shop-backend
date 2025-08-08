package com.example.shopping_mall.domain;

import com.example.shopping_mall.domain.type.Platform;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Generated
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "user_id", unique = true, length = 30)
    private String userId;

    @Column(unique = true)
    private String kakaoId;

    @Column(unique = true)
    private String email;

    @Column(length = 50)
    private String nickname;

    @Column(unique = true, length = 20)
    private String password;

    @Column(unique = true)
    private String memberNumber;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column
    private String name;

    @Column(unique = true, length = 20)
    private String phone_number;

    @Column(unique = true)
    private String address;

    @Column
    private String grade;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
