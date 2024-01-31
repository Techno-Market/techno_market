package com.techno_market.techno_market.domain.user.controller;

import com.techno_market.techno_market.domain.sellArticle.controller.SellArticleController;
import com.techno_market.techno_market.domain.sellArticle.dto.SellArticleCreateDto;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    @PostMapping("/signup")
    public void signup() {

    }

    @AllArgsConstructor
    @Getter
    public static class SignupResponse {
        private final SiteUser siteUser;
    }

    @Data
    public static class SignupRequest {
        //여기부터
    }

    @PostMapping("")
    public RsData<SellArticleCreateDto> write(@Valid SellArticleController.WriteRequest writeRequest) throws Exception{

        RsData<SellArticleCreateDto> rsArticle = this.sellArticleService.create(writeRequest.getSubject(), writeRequest.getContent(),
                writeRequest.getPrice(), writeRequest.getArea(), writeRequest.getCategory(), writeRequest.getDirectly(),
                writeRequest.getParcel(), writeRequest.getPostImage());


        return rsArticle;
    }
}
