package com.example.shopping_mall.controller;

import com.example.shopping_mall.config.jwt.JwtToken;
import com.example.shopping_mall.dto.LoginRequest;
import com.example.shopping_mall.dto.LoginResponse;
import com.example.shopping_mall.dto.SignRequest;
import com.example.shopping_mall.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController("/member")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignRequest signRequest) {
        userService.register(signRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtToken token = userService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token.getAccessToken(), token.getRefreshToken(), "로그인 성공"));
    }
}
