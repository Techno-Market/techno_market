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
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Answer extends BaseEntity {
    @Column(name = "text")
    private String comment;
    @ManyToOne
    private SiteUser user;
    @ManyToOne
    private SellArticle sellArticle;
}
