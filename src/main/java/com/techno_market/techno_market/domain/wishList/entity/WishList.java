package com.techno_market.techno_market.domain.wishList.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class WishList{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private SiteUser siteUser;
    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference
    private SellArticle sellArticle;

}

