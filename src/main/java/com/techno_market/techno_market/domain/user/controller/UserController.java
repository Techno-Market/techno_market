package com.techno_market.techno_market.domain.user.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techno_market.techno_market.domain.sellArticle.controller.SellArticleController;
import com.techno_market.techno_market.domain.sellArticle.dto.SellArticleCreateDto;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.dto.UserDto;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.domain.user.service.AuthTokenService;
import com.techno_market.techno_market.domain.user.service.UserService;
import com.techno_market.techno_market.global.exceptions.GlobalException;
import com.techno_market.techno_market.global.rq.Rq;
import com.techno_market.techno_market.global.rsData.RsData;
import com.techno_market.techno_market.global.security.SecurityUser;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final Rq rq;

    @Data
    public static class SignRequest {
        private String username;
        private String nickName;
        private String password;
        private String email;
        private String name;
        private LocalDate birthDate;
    }

    @PostMapping("/signup")
    public RsData<SiteUser> signup(@Valid @RequestBody SignRequest signRequest) {

        RsData<SiteUser> RsSiteUser = this.userService.signup(signRequest.getUsername(), signRequest.getNickName(), signRequest.getPassword(),signRequest.getEmail(), signRequest.getName(), signRequest.getBirthDate());

        return RsSiteUser;
    }

    @GetMapping("/userNameCheck/{username}")
    public RsData<Boolean> checkUserNameDuplicate(@PathVariable("username") String username) {
        return userService.isUserNameDuplicate(username);
    }

    @GetMapping("/nickNameCheck/{nickName}")
    public RsData<Boolean> checkNickNameDuplicate(@PathVariable("nickName") String nickName) {
        return userService.isNickNameDuplicate(nickName);
    }

    @AllArgsConstructor
    @Getter
    public static class LoginResponseBody {
        private UserDto item;
    }

    @Getter
    @Setter
    public static class LoginRequestBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @PostMapping("/login")
    public RsData<LoginResponseBody> login(@Valid @RequestBody LoginRequestBody body) {
        try {
            RsData<UserService.AuthAndMakeTokensResponseBody> authAndMakeTokensRs =
                    userService.authAndMakeTokens(body.getUsername(), body.getPassword());

            rq.setCrossDomainCookie("refreshToken", authAndMakeTokensRs.getData().getRefreshToken());
            rq.setCrossDomainCookie("accessToken", authAndMakeTokensRs.getData().getAccessToken());

            return RsData.of(
                    authAndMakeTokensRs.getResultCode(),
                    authAndMakeTokensRs.getMsg(),
                    new LoginResponseBody(
                            new UserDto(authAndMakeTokensRs.getData().getUser())
                    )
            );
        } catch (GlobalException e) {
            return RsData.of(e.getCode(), e.getMessage(), null);
        }
    }
}
