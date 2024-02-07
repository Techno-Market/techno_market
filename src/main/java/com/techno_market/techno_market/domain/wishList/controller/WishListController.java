package com.techno_market.techno_market.domain.wishList.controller;

import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.domain.user.service.UserService;
import com.techno_market.techno_market.domain.wishList.entity.WishList;
import com.techno_market.techno_market.domain.wishList.service.WishListService;
import com.techno_market.techno_market.global.rsData.RsData;
import com.techno_market.techno_market.global.security.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wishlists")
public class WishListController {

    private final WishListService wishListService;
    private final UserService userService;
    @AllArgsConstructor
    @Getter
    public static class LikeResponse {
        private final List<WishList> wishLists;
    }
    @GetMapping("")
    public RsData<LikeResponse> wishlist(@AuthenticationPrincipal SecurityUser user) {
            SiteUser siteUser = this.userService.findByUsername(user.getUsername()).orElseThrow();
            List<WishList> wishLists = wishListService.getWishListByUsername(siteUser.getUsername());

        return RsData.of("1", "리스트 출력 성공", new LikeResponse(wishLists));
    }
    @GetMapping("/favorites/{articleId}")
    public ResponseEntity<Map<String, Boolean>> getUserFavorites(@PathVariable(name = "articleId") Long articleId, @AuthenticationPrincipal SecurityUser user) {
        try {
            SiteUser siteUser = this.userService.findByUsername(user.getUsername()).orElseThrow();
            boolean isFavorited = wishListService.isUserFavorited(siteUser.getUsername(), articleId);

            Map<String, Boolean> responseMap = Collections.singletonMap("isFavorited", isFavorited);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", false));
        }
    }


    @PostMapping("/toggleFavorite/{articleId}")
    public ResponseEntity<Map<String, String>> toggleFavorite(@PathVariable(name = "articleId") Long articleId, @AuthenticationPrincipal SecurityUser user) {
        try {
            SiteUser siteUser = this.userService.findByUsername(user.getUsername()).orElseThrow();
            boolean isToggled = wishListService.toggleFavorite(articleId, siteUser.getUsername());

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", isToggled ? "찜 추가 성공" : "찜 해제 성공");

            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "에러 발생: " + e.getMessage()));
        }
    }

}
