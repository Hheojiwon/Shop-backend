package com.example.shopping_mall.service;

import com.example.shopping_mall.domain.Member;
import com.example.shopping_mall.domain.type.Platform;
import com.example.shopping_mall.domain.type.Role;
import com.example.shopping_mall.dto.KakaoInfoDto;
import com.example.shopping_mall.repository.MemberRepository;
import com.example.shopping_mall.security.KakaoMemberDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoInfoDto kakaoInfo = objectMapper.convertValue(oAuth2User.getAttributes(), KakaoInfoDto.class);

        String kakaoId = String.valueOf(kakaoInfo.getId());
        String nickname = kakaoInfo.getKakaoAccount().getProfile().getNickname();
        String email = kakaoInfo.getKakaoAccount().getProfile().getEmail();

        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .userId("kakao_" + kakaoId)
                        .kakaoId(kakaoId)
                        .nickname(nickname)
                        .email(email)
                        .platform(Platform.KAKAO)
                        .role(Role.ROLE_USER)
                        .createdAt(LocalDateTime.now())
                        .isDeleted(false)
                        .build()
                ));

        return new KakaoMemberDetails(member, oAuth2User.getAttributes());
    }
}

