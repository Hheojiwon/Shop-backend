package com.example.shopping_mall.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoInfoDto {

    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    public static class KakaoAccount {
        private Profile profile;
        private String email;

        @Getter
        public static class Profile {
            private String nickname;
        }
    }
}
