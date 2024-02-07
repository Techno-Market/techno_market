package com.techno_market.techno_market.domain.sellArticle.repository;

import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.domain.wishList.entity.WishList;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellArticleRepository extends JpaRepository<SellArticle, Long> {
    Page<SellArticle> findAll(Specification<SellArticle> spec, Pageable pageable);
    Page<SellArticle> findAll(Pageable pageable);
}
