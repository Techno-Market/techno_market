package com.techno_market.techno_market.domain.wishList.entity;

import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class WishList extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUser siteUser;


    @ManyToOne
    @JoinColumn(name = "article_id")
    private SellArticle sellArticle;

}

