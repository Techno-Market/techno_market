package com.techno_market.techno_market.domain.answerArticle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EnableJpaAuditing
public class Answer extends BaseEntity {
    @Column(name = "text")
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private SiteUser user;
    @ManyToOne(fetch = FetchType.LAZY)
    private SellArticle sellArticle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    @JsonIgnore
    private Answer parent;
    @Column(columnDefinition = "TINYINT", length = 1)
    private int secret;
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @Builder
    public Answer(SiteUser user, String comment, int secret, SellArticle sellArticle, Answer parent) {
        this.user = user;
        this.comment = comment;
        this.secret = secret;
        this.sellArticle = sellArticle;
        this.parent = parent;
    }
    public void update(String comment){
        this.comment = comment;
    }
}
