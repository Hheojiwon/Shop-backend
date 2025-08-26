package com.example.shopping_mall.dto.response;

import lombok.Getter;

@Getter
public enum ApiStatus {
    OK(200, "성공"),
    CREATED(201, "생성 완료"),
    NO_CONTENT(204, "내용 없음"),
    BAD_REQUEST(400, "잘못된 요청"),
    UNAUTHORIZED(401, "인증 실패"),
    FORBIDDEN(403, "권한 없음"),
    NOT_FOUND(404, "리소스를 찾을 수 없음"),
    INTERNAL_SERVER_ERROR(500, "서버 내부 에러"),
    DB_ERROR(600, "데이터베이스 에러");

    private final int code;
    private final String defaultMessage;

    ApiStatus(int code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
