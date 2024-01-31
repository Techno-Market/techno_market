package com.techno_market.techno_market.domain.user.service;

import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public SiteUser signup(String userName, String nickName, String password, String email, String name) {
        SiteUser siteUser = SiteUser.builder()
                .userName(userName)
                .nickName(nickName)
                .password(password)
                .email(email)
                .name(name)
                .build();

        userRepository.save(siteUser);

        return siteUser;
    }
}
