package com.example.shopping_mall.controller;

import com.example.shopping_mall.config.jwt.JwtToken;
import com.example.shopping_mall.dto.request.LoginRequest;
import com.example.shopping_mall.dto.response.ApiResponse;
import com.example.shopping_mall.dto.response.ApiStatus;
import com.example.shopping_mall.dto.response.LoginResponse;
import com.example.shopping_mall.dto.request.SignRequest;
import com.example.shopping_mall.security.CustomUser;
import com.example.shopping_mall.service.AuthService;
import com.example.shopping_mall.service.KakaoOauthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final KakaoOauthService kakaoOauthService;

    public AuthController(AuthService authService, KakaoOauthService kakaoOauthService) {
        this.authService = authService;
        this.kakaoOauthService = kakaoOauthService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody SignRequest signRequest) {
        authService.register(signRequest);
        return ResponseEntity
                .status(ApiStatus.CREATED.getCode())
                .body(ApiResponse.res(ApiStatus.CREATED.getCode(), "회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        JwtToken token = authService.login(loginRequest);
        LoginResponse loginResponse = new LoginResponse(token.getAccessToken(), token.getRefreshToken(), "로그인 성공");
        return ResponseEntity
                .status(ApiStatus.OK.getCode())
                .body(ApiResponse.res(ApiStatus.OK.getCode(), null, loginResponse));
    }

    @GetMapping("/kakao")
    public ResponseEntity<String> redirectToKakaoLogin() {
        String uri = kakaoOauthService.getKakaoAuthorizeUri();
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", uri).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@AuthenticationPrincipal CustomUser customUser) {
        String userId = customUser.getUserId(); // 로그인된 회원의 userId
        authService.deleteAccount(userId);

        return ResponseEntity.status(ApiStatus.OK.getCode())
                .body(ApiResponse.res(ApiStatus.OK.getCode(),
                        "회원 탈퇴 성공"));
    }
}
