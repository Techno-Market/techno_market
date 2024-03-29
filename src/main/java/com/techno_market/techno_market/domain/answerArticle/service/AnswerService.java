package com.techno_market.techno_market.domain.answerArticle.service;

import com.techno_market.techno_market.domain.answerArticle.entity.Answer;
import com.techno_market.techno_market.domain.answerArticle.repository.AnswerRepository;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public List<Answer> getList(Long id) {
        List<Answer> answers = this.answerRepository.findAllBySellArticleId(id);
        return answers;
    }
    public Answer getAnswerById(Long id) {
        Optional<Answer> optionalAnswer = this.answerRepository.findById(id);
        if (optionalAnswer.isPresent()) {
            return optionalAnswer.get();
        } else {
            throw new RuntimeException("댓글이 존재하지 않습니다");
        }
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
        } else {
            throw new RuntimeException("유저가 일치하지 않습니다");
        }
        return answer;
    }
}
