package com.techno_market.techno_market.domain.sellArticle.repository;

import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellArticleRepository extends JpaRepository<SellArticle, Long> {
}
