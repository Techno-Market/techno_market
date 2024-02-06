package com.techno_market.techno_market.domain.answerArticle.controller;

import com.techno_market.techno_market.domain.answerArticle.entity.Answer;
import com.techno_market.techno_market.domain.answerArticle.service.AnswerService;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.sellArticle.service.SellArticleService;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.domain.user.service.UserService;
import com.techno_market.techno_market.global.rsData.RsData;
import com.techno_market.techno_market.global.security.SecurityUser;
import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerArticleController {
    private final AnswerService answerService;
    private final UserService userService;
    private final SellArticleService sellArticleService;
    @AllArgsConstructor
    @Getter
    public static class AnswerArticlesResponse {
        private final List<Answer> answers;
    }
    @GetMapping("/{sellArticleId}")
    public RsData<AnswerArticlesResponse> getAnswers(@PathVariable("sellArticleId") Long id) {
        List<Answer> answers = this.answerService.getList();
        return RsData.of("1", "댓글 리스트 불러오기", new AnswerArticlesResponse(answers));
    }
    @AllArgsConstructor
    @Getter
    public static class AnswerArticleResponse {
        private final Answer answer;
    }
    @GetMapping("/{id}")
    public RsData<AnswerArticleResponse> getAnswer(@PathVariable("id") Long id) {
        Answer answer = this.answerService.getAnswerById(id);
        return RsData.of("2", "댓글 불러오기", new AnswerArticleResponse(answer));
    }
    @Data
    public static class WriteRequest{
        private String comment;

    }
    @PostMapping("/{sellArticleId}")
    public RsData<AnswerArticleResponse> create(@PathVariable("sellArticleId") Long id, WriteRequest writeRequest, @AuthenticationPrincipal SecurityUser user) {
        SiteUser writer = this.userService.findByUsername(user.getUsername()).orElseThrow();
        SellArticle sellArticle = this.sellArticleService.getArticle(id);
        Answer answer = this.answerService.create(writer, writeRequest.getComment(), sellArticle);
        return RsData.of("3", "댓글 작성 성공", new AnswerArticleResponse(answer));
    }
    @PatchMapping("/{id}")
    public RsData<AnswerArticleResponse> modify(@PathVariable("id") Long id, WriteRequest writeRequest, @AuthenticationPrincipal SecurityUser user) {
        SiteUser writer = this.userService.findByUsername(user.getUsername()).orElseThrow();
        Answer answer = this.answerService.getAnswerById(id);
        this.answerService.modify(writer, answer, writeRequest.getComment());
        return RsData.of("4", "댓글 수정 성공", new AnswerArticleResponse(answer));
    }
    @DeleteMapping("/{id}")
    public RsData<AnswerArticleResponse> delete(@PathVariable("id") Long id, @AuthenticationPrincipal SecurityUser user) {
        SiteUser writer = this.userService.findByUsername(user.getUsername()).orElseThrow();
        Answer answer = this.answerService.getAnswerById(id);
        this.answerService.delete(writer, answer);
        return RsData.of("5", "댓글 삭제 성공", new AnswerArticleResponse(answer));
    }
}
