package com.example.shopping_mall.controller;

import com.example.shopping_mall.dto.request.EmailRequest;
import com.example.shopping_mall.dto.response.ApiResponse;
import com.example.shopping_mall.dto.response.ApiStatus;
import com.example.shopping_mall.service.AuthCodeService;
import com.example.shopping_mall.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;
    private final AuthCodeService authCodeService;

    public MailController(MailService mailService, AuthCodeService authCodeService) {
        this.mailService = mailService;
        this.authCodeService = authCodeService;
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<Void>> sendMail(@RequestBody EmailRequest request) throws MessagingException {
        String email = request.getEmail();
        String code = mailService.createCode();
        authCodeService.saveAuthCode(email, code);
        mailService.sendAuthNum(email, code);
        return ResponseEntity
                .status(ApiStatus.OK.getCode())
                .body(ApiResponse.res(ApiStatus.OK.getCode(), "인증번호가 " + email + " 로 발송되었습니다."));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = authCodeService.verifyAuthCode(email, code);
        if (isValid) {
            return ResponseEntity.ok(ApiResponse.res(ApiStatus.OK.getCode(), "인증 성공"));
        } else {
            return ResponseEntity.status(400)
                    .body(ApiResponse.res(ApiStatus.BAD_REQUEST.getCode(), "인증 실패"));
        }
    }

}
