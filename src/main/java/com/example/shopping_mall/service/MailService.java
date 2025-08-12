package com.example.shopping_mall.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    // 인증번호 생성
    private String createCode() {
        int code = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }

    public void sendAuthNum(String email) throws MessagingException {
        String random = createCode(); // 인증번호 생성

        String htmlContent = "<html>" +
                "<body>" +
                "<h1 style='color: orange;'>인증번호 발송</h1>" +
                "<p>인증번호는 <b>" + random + "</b> 입니다.</p>" +
                "<p>인증번호는 5분간 유효합니다."+
                "</body>" +
                "</html>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("인증번호");
        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}
