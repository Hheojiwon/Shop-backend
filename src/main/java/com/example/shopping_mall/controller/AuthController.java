package com.example.shopping_mall.controller;

import com.example.shopping_mall.config.jwt.JwtToken;
import com.example.shopping_mall.dto.LoginRequest;
import com.example.shopping_mall.dto.LoginResponse;
import com.example.shopping_mall.dto.SignRequest;
import com.example.shopping_mall.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController("/member")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignRequest signRequest) {
        authService.register(signRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtToken token = authService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token.getAccessToken(), token.getRefreshToken(), "로그인 성공"));
    }
}
