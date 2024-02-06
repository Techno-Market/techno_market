package com.techno_market.techno_market.domain.sellArticle.controller;

import com.techno_market.techno_market.domain.photo.entity.Photo;
import com.techno_market.techno_market.domain.photo.service.PhotoService;
import com.techno_market.techno_market.domain.sellArticle.dto.SellArticleCreateDto;
import com.techno_market.techno_market.domain.sellArticle.entity.CategoryType;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.sellArticle.service.SellArticleService;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.domain.user.service.UserService;
import com.techno_market.techno_market.global.rsData.RsData;
import com.techno_market.techno_market.global.security.SecurityUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class SellArticleController {
    private final SellArticleService sellArticleService;
    private final PhotoService fileService;
    private final UserService userService;

    @AllArgsConstructor
    @Getter
    public static class ArticlesResponse {
        private final Page<SellArticle> articles;
    }


    @GetMapping("")
    public RsData<ArticlesResponse> articles(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<SellArticle> sellArticles = this.sellArticleService.getList(page);

        return RsData.of("1", "성공", new ArticlesResponse(sellArticles));
    }

    @AllArgsConstructor
    @Getter
    public static class ArticleResponse {
        private final SellArticle sellArticle;
    }

    @GetMapping("/{id}")
    public RsData<ArticleResponse> article(@PathVariable("id") Long id) {
        SellArticle sellArticle = this.sellArticleService.getArticle(id);
        return RsData.of("2", "성공", new ArticleResponse(sellArticle));
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
        private List<MultipartFile> postImage;
    }

    @PostMapping("")
    public RsData<SellArticleCreateDto> write(@Valid WriteRequest writeRequest, @AuthenticationPrincipal SecurityUser user) throws Exception{
        SiteUser author = this.userService.findByUsername(user.getUsername()).orElseThrow();
        RsData<SellArticleCreateDto> rsArticle = this.sellArticleService.create(writeRequest.getSubject(), writeRequest.getContent(),
                writeRequest.getPrice(), writeRequest.getArea(), writeRequest.getCategory(), writeRequest.getDirectly(),
                writeRequest.getParcel(), writeRequest.getPostImage(),author);
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
    public RsData<SellArticle> modify(@Valid WriteRequest writeRequest, @PathVariable("id") Long id) throws Exception{

        RsData<SellArticle> modifyArticle = sellArticleService.modify(id,
                writeRequest.getSubject(),
                writeRequest.getContent(),
                writeRequest.getPrice(),
                writeRequest.getArea(),
                writeRequest.getCategory(),
                writeRequest.getDirectly(),
                writeRequest.getParcel(),
                writeRequest.getPostImage());

        return modifyArticle;
    }


    @DeleteMapping("/{id}")
    public RsData<SellArticle> delete(@PathVariable("id") Long id) {
        SellArticle sellArticle = this.sellArticleService.getArticle(id);
        RsData<SellArticle> articleRsData = this.sellArticleService.delete(sellArticle);
        return articleRsData;
    }
    @AllArgsConstructor
    @Getter
    public static class SearchArticlesResponse {
        private final Page<SellArticle> articles;
    }
    @GetMapping("/search")
    public RsData<SearchArticlesResponse> searchArticles(@RequestParam(value = "kw", defaultValue = "") String kw, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<SellArticle> articles = this.sellArticleService.getSearchList(kw, page);
        return RsData.of("3", "성공", new SearchArticlesResponse(articles));
    }
    @GetMapping("/category")
    public RsData<SearchArticlesResponse> categoryArticles(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "category", defaultValue = "ALL") CategoryType categoryType) {
        Page<SellArticle> articles = this.sellArticleService.getCategoryList(page, categoryType);
        return RsData.of("4", "성공", new SearchArticlesResponse(articles));
    }
    @GetMapping("/priceDesc")
    public RsData<SearchArticlesResponse> HighestPriceArticles(@RequestParam(value = "kw", defaultValue = "") String kw, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "category", defaultValue = "ALL") CategoryType categoryType) {
        Page<SellArticle> priceArticles = this.sellArticleService.getHighPriceArticles(kw, page, categoryType);
        return RsData.of("5", "성공", new SearchArticlesResponse(priceArticles));
    }
    @GetMapping("/priceAsc")
    public RsData<SearchArticlesResponse> LowestPriceArticles(@RequestParam(value = "kw", defaultValue = "") String kw, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "category", defaultValue = "ALL") CategoryType categoryType) {
        Page<SellArticle> priceArticles = this.sellArticleService.getLowPriceArticles(kw, page, categoryType);
        return RsData.of("6", "성공", new SearchArticlesResponse(priceArticles));
    }

}
