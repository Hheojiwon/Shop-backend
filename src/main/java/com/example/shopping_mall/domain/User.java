package com.example.shopping_mall.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Generated
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "user_id", unique = true, length = 30)
    private String userId;

    @Column(unique = true, length = 20)
    private String password;

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
