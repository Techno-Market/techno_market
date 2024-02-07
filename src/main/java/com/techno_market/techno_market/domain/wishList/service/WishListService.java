package com.techno_market.techno_market.domain.wishList.service;

import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.sellArticle.repository.SellArticleRepository;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.domain.user.repository.UserRepository;
import com.techno_market.techno_market.domain.wishList.entity.WishList;
import com.techno_market.techno_market.domain.wishList.repository.WishListRepository;
import com.techno_market.techno_market.global.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final SellArticleRepository sellArticleRepository;
    private final UserRepository userRepository;

    public List<WishList> getWishListByUsername(String username) {
        return wishListRepository.findBySiteUserUsername(username);
    }

    @Transactional
    public boolean toggleFavorite(Long articleId, String username) {
        SellArticle sellArticle = sellArticleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Matching not found with id: " + articleId));

        WishList wishList = wishListRepository.findBySiteUserUsernameAndSellArticleId(username, articleId);
        if (wishList != null) {
            wishListRepository.delete(wishList);
            sellArticle.getWishLists().remove(wishList);
            return false; // 찜 해제
        } else {
            SiteUser siteUser = this.userRepository.findByUsername(username)
                    .orElseThrow(() -> new DataNotFoundException("siteuser not found"));

            wishList = new WishList();
            wishList.setSiteUser(siteUser);
            wishList.setSellArticle(sellArticle);
            wishListRepository.save(wishList);

            sellArticle.getWishLists().add(wishList);
            return true; // 찜 추가
        }
    }
    @Transactional
    public boolean isUserFavorited(String username, Long articleId) {
        if (wishListRepository.existsBySiteUserUsername(username) && wishListRepository.existsBySellArticleId(articleId)) {
            return true;
        } else return false;
    }
}
