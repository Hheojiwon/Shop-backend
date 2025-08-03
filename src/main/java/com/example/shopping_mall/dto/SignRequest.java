package com.example.shopping_mall.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignRequest {

    private String userId;
    private String password;
    private String name;
    private String phone_number;
    private String address;
    private String grade;
}
