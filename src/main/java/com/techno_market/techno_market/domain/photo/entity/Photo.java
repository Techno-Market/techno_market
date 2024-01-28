package com.techno_market.techno_market.domain.photo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "article_id")
    private SellArticle sellArticle;

    @Column(nullable = false)
    private String origFileName;  // 파일 원본명

    @Column(nullable = false)
    private String filePath;  // 파일 저장 경로

    private Long fileSize;

    @Builder
    public Photo(String origFileName, String filePath, Long fileSize){
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    // Board 정보 저장
    public void setSellArticle(SellArticle sellArticle){
        this.sellArticle = sellArticle;

        // 게시글에 현재 파일이 존재하지 않는다면
        if(!sellArticle.getPhoto().contains(this))
            // 파일 추가
            sellArticle.getPhoto().add(this);
    }
}
