package com.techno_market.techno_market.domain.answerArticle.repository;

import com.techno_market.techno_market.domain.answerArticle.entity.Answer;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllBySellArticleId(Long id);
}
