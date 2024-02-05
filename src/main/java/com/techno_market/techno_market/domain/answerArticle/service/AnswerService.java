package com.techno_market.techno_market.domain.answerArticle.service;

import com.techno_market.techno_market.domain.answerArticle.entity.Answer;
import com.techno_market.techno_market.domain.answerArticle.repository.AnswerRepository;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public List<Answer> getList() {
        List<Answer> answers = this.answerRepository.findAll();
        return answers;
    }
    public Answer getAnswerById(Long id) {
        Answer answer = this.answerRepository.findById(id).orElseThrow();
        return answer;
    }
    public Answer create(SiteUser user, String comment, Answer parent, SellArticle sellArticle) {
        Answer answer = Answer.builder()
                .comment(comment)
                .parent(parent)
                .user(user)
                .sellArticle(sellArticle)
                .build();
        return this.answerRepository.save(answer);
    }
    public Answer delete(Answer answer) {
        this.answerRepository.delete(answer);
        return answer;
    }
}
