package com.techno_market.techno_market.domain.sellArticle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class SellArticleCreateDto {
    private String subject;
    private String content;
    private int price;
    private String area;
    private String category;
    private Boolean directly;
    private Boolean parcel;
}

