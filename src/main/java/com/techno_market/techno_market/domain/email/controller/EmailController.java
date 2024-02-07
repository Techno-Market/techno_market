package com.techno_market.techno_market.domain.email.controller;

import com.techno_market.techno_market.domain.email.dto.EmailMessage;
import com.techno_market.techno_market.domain.email.dto.EmailPostDto;
import com.techno_market.techno_market.domain.email.dto.EmailResponseDto;
import com.techno_market.techno_market.domain.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<EmailResponseDto> sendJoinMail(@RequestBody EmailPostDto emailPostDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("[Techno Market] 이메일 인증코드")
                .build();

        String code = emailService.sendMail(emailMessage, "email");

        EmailResponseDto emailResponseDto = new EmailResponseDto();
        emailResponseDto.setCode(code);

        return ResponseEntity.ok(emailResponseDto);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestBody EmailPostDto emailPostDto) {
        String userEmail = emailPostDto.getEmail();
        String userEnteredCode = emailPostDto.getCode();

        if (emailService.verifyCode(userEmail, userEnteredCode)) {
            return ResponseEntity.ok("인증 성공");
        } else {
            return ResponseEntity.badRequest().body("인증 실패");
        }
    }
}
