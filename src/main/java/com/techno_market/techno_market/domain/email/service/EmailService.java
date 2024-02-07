package com.techno_market.techno_market.domain.email.service;

import com.techno_market.techno_market.domain.email.dto.EmailMessage;
import com.techno_market.techno_market.domain.email.entity.EmailVerification;
import com.techno_market.techno_market.domain.email.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;


    public String sendMail(EmailMessage emailMessage, String type) {
        String code = createCode();
        String htmlContent = createHtmlContent(code, type);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();


        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
            mimeMessageHelper.setText(htmlContent, true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

            log.info("Success");
            saveVerificationCode(emailMessage.getTo(), code);
            return code;

        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
    }
    public boolean verifyCode(String email, String userEnteredCode) {
        Optional<EmailVerification> optionalEmailVerification =
                emailRepository.findByEmailAndVerificationCode(email, userEnteredCode);
        return optionalEmailVerification.isPresent() &&
                optionalEmailVerification.get().getVerificationCode().equals(userEnteredCode);
    }

    // 인증번호 저장
    @Transactional
    public void saveVerificationCode(String email, String verificationCode) {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setVerificationCode(verificationCode);
        emailRepository.save(emailVerification);
    }
    // 인증번호 생성 메서드
    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    // HTML 내용 생성 메서드
    public String createHtmlContent(String code, String type) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html>");
        htmlContent.append("<body>");
        htmlContent.append("<div style=\"margin:100px;\">");
        htmlContent.append("<h1> 안녕하세요.</h1>");
        htmlContent.append("<h1> Techno Market 입니다.</h1>");
        htmlContent.append("<br>");
        htmlContent.append("<p> 아래 코드를 회원가입 창으로 돌아가 입력해주세요.</p>");
        htmlContent.append("<br>");
        htmlContent.append("<div align=\"center\" style=\"border:1px solid black; font-family:verdana;\">");
        htmlContent.append("<h3 style=\"color:blue\"> 회원가입 인증 코드 입니다. </h3>");
        htmlContent.append("<div style=\"font-size:130%\">").append(code).append("</div>");
        htmlContent.append("</div>");
        htmlContent.append("<br/>");
        htmlContent.append("</div>");
        htmlContent.append("</body>");
        htmlContent.append("</html>");

        return htmlContent.toString();
    }
}
