package com.techno_market.techno_market.domain.wishList.repository;

import com.techno_market.techno_market.domain.wishList.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}
