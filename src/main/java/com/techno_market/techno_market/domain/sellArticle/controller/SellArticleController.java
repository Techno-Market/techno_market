package com.techno_market.techno_market.domain.sellArticle.controller;

import com.techno_market.techno_market.domain.photo.service.PhotoService;
import com.techno_market.techno_market.domain.sellArticle.dto.SellArticleCreateDto;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.sellArticle.service.SellArticleService;
import com.techno_market.techno_market.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class SellArticleController {
    private final SellArticleService sellArticleService;
    private final PhotoService fileService;

    @AllArgsConstructor
    @Getter
    public static class ArticlesResponse {
        private final List<SellArticle> articles;
    }


    @GetMapping("")
    public RsData<ArticlesResponse> articles() {
        List<SellArticle> sellArticles = this.sellArticleService.getList();

        return RsData.of("S-1", "성공", new ArticlesResponse(sellArticles));
    }

    @AllArgsConstructor
    @Getter
    public static class ArticleResponse {
        private final SellArticle sellArticle;
    }

    @GetMapping("/{id}")
    public RsData<ArticleResponse> article(@PathVariable("id") Long id) {
        SellArticle sellArticle = this.sellArticleService.getArticle(id);

        return RsData.of("S-2", "성공", new ArticleResponse(sellArticle));
    }

    @AllArgsConstructor
    @Getter
    public static class WriteResponse {
        private final SellArticle sellArticle;
    }

    @Data
    public static class WriteRequest {
        @NotBlank
        private String subject;
        @NotBlank
        private String content;
        @NotNull
        private int price;
        @NotBlank
        private String area;
        @NotBlank
        private String category;
        private Boolean directly;
        private Boolean parcel;
        @NotNull
        private List<MultipartFile> postImage;
    }

    @PostMapping("")
    public RsData<SellArticleCreateDto> write(@Valid WriteRequest writeRequest) throws Exception{

        RsData<SellArticleCreateDto> rsArticle = this.sellArticleService.create(writeRequest.getSubject(), writeRequest.getContent(),
                writeRequest.getPrice(), writeRequest.getArea(), writeRequest.getCategory(), writeRequest.getDirectly(),
                writeRequest.getParcel(), writeRequest.getPostImage());


        return rsArticle;
    }

    @AllArgsConstructor
    @Getter
    public static class ModifyResponse {
        private final SellArticle sellArticle;
    }

    @Data
    public static class ModifyRequest {
        private String subject;
        private String content;
        private int price;
        private String area;
    }

    @PatchMapping("/{id}")
    public RsData<SellArticle> modify(@Valid @RequestBody WriteRequest writeRequest, @PathVariable("id") Long id) {
        SellArticle sellArticle = this.sellArticleService.getArticle(id);

        RsData<SellArticle> modifyArticle = sellArticleService.modify(sellArticle, writeRequest.getSubject(), writeRequest.getContent(), writeRequest.getPrice(), writeRequest.getArea());

        return modifyArticle;
    }


    @DeleteMapping("/{id}")
    public RsData<SellArticle> delete(@PathVariable("id") Long id) {
        SellArticle sellArticle = this.sellArticleService.getArticle(id);
        RsData<SellArticle> articleRsData = this.sellArticleService.delete(sellArticle);
        return articleRsData;
    }

}
