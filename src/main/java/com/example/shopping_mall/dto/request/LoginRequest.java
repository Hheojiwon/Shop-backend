package com.example.shopping_mall.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginRequest {

    private String userId;
    private String password;
}
