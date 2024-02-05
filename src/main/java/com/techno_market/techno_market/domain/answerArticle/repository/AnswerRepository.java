package com.techno_market.techno_market.domain.answerArticle.repository;

import com.techno_market.techno_market.domain.answerArticle.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
