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
    public Answer create(SiteUser user, String comment, SellArticle sellArticle) {
        Answer answer = Answer.builder()
                .comment(comment)
                .user(user)
                .sellArticle(sellArticle)
                .createDate(LocalDateTime.now())
                .build();
        return this.answerRepository.save(answer);
    }
    public Answer modify(SiteUser writer, Answer answer, String comment) {
        if (writer.getId().equals(answer.getUser().getId())){
            answer.setComment(comment);
            answer.setModifyDate(LocalDateTime.now());
            this.answerRepository.save(answer);
        } else {
            throw new RuntimeException("유저가 일치하지 않습니다");
        }
        return answer;
    }
    public Answer delete(SiteUser user, Answer answer) {
        if (user.getUsername().equals(answer.getUser().getUsername())) {
           this.answerRepository.delete(answer);
        }
        return answer;
    }
}
