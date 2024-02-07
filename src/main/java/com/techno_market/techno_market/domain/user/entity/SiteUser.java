package com.techno_market.techno_market.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.wishList.entity.WishList;
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
@Setter
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

    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<WishList> wishLists;
    public List<String> getAuthoritiesAsStringList() {
        return List.of("ROLE_MEMBER");
    }
}
