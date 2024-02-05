package com.techno_market.techno_market.domain.answerArticle.controller;

import com.techno_market.techno_market.domain.answerArticle.entity.Answer;
import com.techno_market.techno_market.domain.answerArticle.service.AnswerService;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.service.UserService;
import com.techno_market.techno_market.global.rsData.RsData;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class AnswerArticleController {
    private final AnswerService answerService;
    private final UserService userService;
    @AllArgsConstructor
    @Getter
    public static class AnswerArticlesResponse {
        private final List<Answer> answers;
    }
    @GetMapping("")
    public RsData<AnswerArticlesResponse> getAnswers() {
        List<Answer> answers = this.answerService.getList();
        return RsData.of("S-1", "댓글 리스트 불러오기", new AnswerArticlesResponse(answers));
    }
    @AllArgsConstructor
    @Getter
    public static class AnswerArticleResponse {
        private final Answer answer;
    }
    @GetMapping("/{id}")
    public RsData<AnswerArticleResponse> getAnswer(@PathVariable("id") Long id) {
        Answer answer = this.answerService.getAnswerById(id);
        return RsData.of("S-2", "댓글 불러오기", new AnswerArticleResponse(answer));
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class WriteRequest{
        private String comment;
        private Long parentId;
        private int secret;

        public Answer toEntity(Answer parent, SellArticle sellArticle){
            Answer answer = Answer.builder()
                    .sellArticle(sellArticle)
                    .comment(comment)
                    .secret(secret)
                    .parent(parent)
                    .build();
            return answer;
        }

    }
//    @PostMapping("")
//    public RsData<AnswerArticleResponse> create(WriteRequest writeRequest, @AuthenticationPrincipal SiteUser user) {
//        SiteUser writer = this.userService.findByUsername(user.getUserName());
//        return RsData.of("S-3", "댓글 작성 성공", new AnswerArticleResponse(answer));
//    }
    @DeleteMapping("/{id}")
    public RsData<AnswerArticleResponse> delete(@PathVariable("id") Long id) {
        Answer answer = this.answerService.getAnswerById(id);
        this.answerService.delete(answer);
        return RsData.of("S-4", "댓글 삭제 성공", new AnswerArticleResponse(answer));
    }
}
