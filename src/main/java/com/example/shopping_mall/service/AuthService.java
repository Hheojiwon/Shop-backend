package com.example.shopping_mall.service;

import com.example.shopping_mall.config.jwt.JwtToken;
import com.example.shopping_mall.config.jwt.JwtTokenProvider;
import com.example.shopping_mall.domain.Member;
import com.example.shopping_mall.dto.request.LoginRequest;
import com.example.shopping_mall.dto.request.SignRequest;
import com.example.shopping_mall.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void register(SignRequest signRequest) {
        Member member = Member.builder()
                .userId(signRequest.getUserId())
                .password(passwordEncoder.encode(signRequest.getPassword()))
                .name(signRequest.getName())
                .phone_number(signRequest.getPhone_number())
                .address(signRequest.getAddress())
                .grade(signRequest.getGrade())
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build(); // 빌더 사용

        memberRepository.save(member);
    }

    public JwtToken login(LoginRequest loginRequest) {
        Member member = memberRepository.findByUserId(loginRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호 일치 X");
        }
        JwtToken jwtToken = jwtTokenProvider.generateToken(
                new UsernamePasswordAuthenticationToken(member.getUserId(), null, List.of(new SimpleGrantedAuthority("ROLE_USER")))
        );
        return jwtToken;
    }

    public void deleteAccount(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        member.setIsDeleted();
        memberRepository.save(member);
    }
}
