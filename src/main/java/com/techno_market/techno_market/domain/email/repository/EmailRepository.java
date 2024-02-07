package com.techno_market.techno_market.domain.email.repository;

import com.techno_market.techno_market.domain.email.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByEmailAndVerificationCode(String email, String verificationCode);
}
