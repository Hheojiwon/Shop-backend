package com.example.shopping_mall.controller;

import com.example.shopping_mall.dto.response.ApiResponse;
import com.example.shopping_mall.dto.response.ApiStatus;
import com.example.shopping_mall.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    private MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<Void>> sendMail(@RequestBody String email) throws MessagingException {
        mailService.sendAuthNum(email);
        return ResponseEntity
                .status(ApiStatus.OK.getCode())
                .body(ApiResponse.res(ApiStatus.OK.getCode(), "인증번호가 " + email + " 로 발송되었습니다."));
    }
}
