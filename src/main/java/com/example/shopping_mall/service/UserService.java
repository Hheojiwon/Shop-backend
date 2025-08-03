package com.example.shopping_mall.service;

import com.example.shopping_mall.config.jwt.JwtToken;
import com.example.shopping_mall.config.jwt.JwtTokenProvider;
import com.example.shopping_mall.domain.User;
import com.example.shopping_mall.dto.LoginRequest;
import com.example.shopping_mall.dto.SignRequest;
import com.example.shopping_mall.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void register(SignRequest signRequest) {
        User user = User.builder()
                .userId(signRequest.getUserId())
                .password(passwordEncoder.encode(signRequest.getPassword()))
                .name(signRequest.getName())
                .phone_number(signRequest.getPhone_number())
                .address(signRequest.getAddress())
                .grade(signRequest.getGrade())
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build(); // 빌더 사용

        userRepository.save(user);
    }

    public JwtToken login(LoginRequest loginRequest) {
        User user = userRepository.findUserByUserId(loginRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호 일치 X");
        }
        JwtToken jwtToken = jwtTokenProvider.generateToken(
                new UsernamePasswordAuthenticationToken(user.getUserId(), null, List.of(new SimpleGrantedAuthority("ROLE_USER")))
        );

        return jwtToken;
    }
}
