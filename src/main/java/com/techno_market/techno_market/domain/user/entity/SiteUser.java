package com.techno_market.techno_market.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EnableJpaAuditing
public class SiteUser extends BaseEntity {
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickName;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String email;

    private String name;

    private LocalDate birthDate;

    private String refreshToken;

    @OneToMany(mappedBy = "author")  // user 필드를 참조하는 역방향 연관관계 설정
    @JsonIgnore
    private List<SellArticle> sellArticles = new ArrayList<>();

    public List<String> getAuthoritiesAsStringList() {
        return List.of("ROLE_MEMBER");
    }

    public void addSellArticle(SellArticle sellArticle) {
        this.sellArticles.add(sellArticle);

        // 유저에게 이 게시글이 설정되어 있지 않은 경우
        if (sellArticle.getAuthor() != this) {
            // 게시글에 이 유저를 설정
            sellArticle.setAuthor(this);
        }
    }
}
