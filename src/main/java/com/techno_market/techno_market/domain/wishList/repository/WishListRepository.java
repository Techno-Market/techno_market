package com.techno_market.techno_market.domain.wishList.repository;

import com.techno_market.techno_market.domain.wishList.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findBySiteUserUsername(String username);
    boolean existsBySiteUserUsername(String username);
    boolean existsBySellArticleId(Long sellArticleId);
    WishList findBySiteUserUsernameAndSellArticleId(String username, Long sellArticleId);
}
