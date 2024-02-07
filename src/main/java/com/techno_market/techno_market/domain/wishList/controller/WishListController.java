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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
        List<WishList> wishList = wishListService.getWishListByUsername(siteUser.getUsername());
        return RsData.of("1", "찜목록 불러오기 성공", new LikeResponse(wishList));
    }


    @PostMapping("/toggleFavorite/{sellArticleId}")
    public ResponseEntity<String> toggleFavorite(@PathVariable(name = "sellArticleId") Long sellArticleId, @AuthenticationPrincipal SecurityUser user) {
        SiteUser siteUser = this.userService.findByUsername(user.getUsername()).orElseThrow();
        wishListService.toggleFavorite(sellArticleId, siteUser.getUsername());
        return ResponseEntity.ok("Toggle successful");
    }

    @PostMapping("/deleteWishList/{wishListId}")
    public ResponseEntity<String> deleteWishItem(@PathVariable(name = "wishListId") Long wishListId) {
        wishListService.deleteWishItem(wishListId);
        return ResponseEntity.ok("Delete successful");
    }
}
