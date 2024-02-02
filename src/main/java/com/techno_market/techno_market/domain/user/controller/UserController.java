package com.techno_market.techno_market.domain.user.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techno_market.techno_market.domain.sellArticle.controller.SellArticleController;
import com.techno_market.techno_market.domain.sellArticle.dto.SellArticleCreateDto;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.domain.user.service.UserService;
import com.techno_market.techno_market.global.rsData.RsData;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Data
    public static class SignRequest {
        private String userName;
        private String nickName;
        private String password;
        private String email;
        private String name;
        private LocalDate birthDate;
    }

    @PostMapping("/signup")
    public RsData<SiteUser> signup(@Valid @RequestBody SignRequest signRequest) {

        RsData<SiteUser> RsSiteUser = this.userService.signup(signRequest.getUserName(), signRequest.getNickName(), signRequest.getPassword(),signRequest.getEmail(), signRequest.getName(), signRequest.getBirthDate());

        return RsSiteUser;
    }

    @GetMapping("/userNameCheck/{userName}")
    public RsData<Boolean> checkUserNameDuplicate(@PathVariable("userName") String userName) {
        return userService.isUserNameDuplicate(userName);
    }

    @GetMapping("/nickNameCheck/{nickName}")
    public RsData<Boolean> checkNickNameDuplicate(@PathVariable("nickName") String nickName) {
        return userService.isNickNameDuplicate(nickName);
    }
}
