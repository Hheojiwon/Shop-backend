package com.example.shopping_mall.service;

import com.example.shopping_mall.domain.Member;
import com.example.shopping_mall.domain.type.Platform;
import com.example.shopping_mall.dto.KakaoInfoDto;
import com.example.shopping_mall.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User kakaoUser = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "kakao". 나중에 여러 소셜 로그인 제공자 동시에 사용할때 씀

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoInfoDto kakaoInfo = objectMapper.convertValue(kakaoUser.getAttributes(), KakaoInfoDto.class);

        String kakaoId = String.valueOf(kakaoInfo.getId());
        String email = kakaoInfo.getKakaoAccount().getEmail();
        String nickname = kakaoInfo.getKakaoAccount().getProfile().getNickname();

        // 회원 존재 여부 확인
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .userId("kakao_" + kakaoId)
                        .kakaoId(kakaoId)
                        .email(email)
                        .nickname(nickname)
                        .platform(Platform.KAKAO)
                        .createdAt(LocalDateTime.now())
                        .isDeleted(false)
                        .build()
                ));

        // 시큐리티 세션에 저장할 OAuth2User 객체 생성
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                kakaoUser.getAttributes(),
                "id" // userNameAttributeName (고유키)
        );
    }
}

