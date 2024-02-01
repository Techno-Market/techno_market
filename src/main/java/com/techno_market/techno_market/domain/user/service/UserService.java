package com.techno_market.techno_market.domain.user.service;

import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.domain.user.repository.UserRepository;
import com.techno_market.techno_market.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public RsData<SiteUser> signup(String userName, String nickName, String password, String email, String name, LocalDate birthDate) {
        SiteUser siteUser = SiteUser.builder()
                .userName(userName)
                .nickName(nickName)
                .password(passwordEncoder.encode(password))
                .email(email)
                .name(name)
                .birthDate(birthDate)
                .build();

        userRepository.save(siteUser);

        return RsData.of("S-1", "회원가입 완료", siteUser);
    }
}
