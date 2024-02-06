package com.techno_market.techno_market.domain.user.service;

import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.domain.user.repository.UserRepository;
import com.techno_market.techno_market.global.exceptions.GlobalException;
import com.techno_market.techno_market.global.rsData.RsData;
import com.techno_market.techno_market.global.security.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;
    public RsData<SiteUser> signup(String username, String nickName, String password, String email, String name, LocalDate birthDate) {
        SiteUser siteUser = SiteUser.builder()
                .username(username)
                .nickName(nickName)
                .password(passwordEncoder.encode(password))
                .email(email)
                .name(name)
                .birthDate(birthDate)
                .build();

        userRepository.save(siteUser);

        return RsData.of("1", "회원가입 완료", siteUser);
    }

    public RsData<Boolean> isUserNameDuplicate(String username) {
        Optional<SiteUser> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return RsData.of("2", "중복", true);
        } else {
            return RsData.of("3", "사용 가능", false);
        }
    }

    public RsData<Boolean> isNickNameDuplicate(String nickName) {
        SiteUser user = userRepository.findByNickName(nickName);
        if (user != null) {
            return RsData.of("2", "중복", true);
        } else {
            return RsData.of("3", "사용 가능", false);
        }
    }

    public Optional<SiteUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public SiteUser findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public boolean passwordMatches(SiteUser user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @AllArgsConstructor
    @Getter
    public static class AuthAndMakeTokensResponseBody {
        private SiteUser user;
        private String accessToken;
        private String refreshToken;
    }

    @Transactional
    public RsData<AuthAndMakeTokensResponseBody> authAndMakeTokens(String username, String password) {
        SiteUser user = findByUsername(username)
                .orElseThrow(() -> new GlobalException("400-1", "해당 유저가 존재하지 않습니다."));

        if (!passwordMatches(user, password)) {
            throw new GlobalException("400-2", "비밀번호가 일치하지 않습니다.");
        }

        String refreshToken = authTokenService.genRefreshToken(user);
        String accessToken = authTokenService.genAccessToken(user);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return RsData.of(
                "200-1",
                "로그인 성공",
                new AuthAndMakeTokensResponseBody(user, accessToken, refreshToken)
        );
    }

    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = authTokenService.getDataFrom(accessToken);

        long id = (long) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        List<String> authorities = (List<String>) payloadBody.get("authorities");

        return new SecurityUser(
                id,
                username,
                "",
                authorities.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }

    public boolean validateToken(String token) {
        return authTokenService.validateToken(token);
    }

    public RsData<String> refreshAccessToken(String refreshToken) {
        SiteUser user = userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new GlobalException("400-1", "존재하지 않는 리프레시 토큰입니다."));

        String accessToken = authTokenService.genAccessToken(user);

        return RsData.of("200-1", "토큰 갱신 성공", accessToken);
    }

    @Transactional
    public String genAccessToken(SiteUser user) {
        return authTokenService.genAccessToken(user);
    }
}
