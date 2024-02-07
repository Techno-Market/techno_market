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
    public void toggleFavorite(Long sellArticleId, String username) {
        SellArticle sellArticle = sellArticleRepository.findById(sellArticleId)
                .orElseThrow(() -> new RuntimeException("Matching not found with id: " + sellArticleId));

        WishList wishList = wishListRepository.findBySiteUserUsernameAndSellArticleId(username, sellArticleId);
        if (wishList != null) {
            // Remove matching from wish list
            wishListRepository.delete(wishList);

            // Remove wish list from matching
            sellArticle.getWishLists().remove(wishList);
        } else {
            SiteUser siteUser = this.userRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("siteuser not found"));

            wishList = new WishList();
            wishList.setSiteUser(siteUser);
            wishList.setSellArticle(sellArticle); // Set matching relationship
            wishListRepository.save(wishList);

            // Add wish list to matching
            sellArticle.getWishLists().add(wishList);
        }
    }

    @Transactional
    public void deleteWishItem(Long wishListId) {
        // 삭제 전용 로직을 구현
        WishList wishList = wishListRepository.findById(wishListId)
                .orElseThrow(() -> new RuntimeException("WishList not found with id: " + wishListId));

        SellArticle sellArticle = wishList.getSellArticle();
        sellArticle.getWishLists().remove(wishList);
        wishListRepository.delete(wishList);
    }

}
