package com.techno_market.techno_market.domain.sellArticle.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.techno_market.techno_market.domain.answerArticle.entity.Answer;
import com.techno_market.techno_market.domain.photo.entity.Photo;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SellArticle {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String subject;
    private String content;
    private int price;
    private String area;
    private String category;
    private Boolean directly;
    private Boolean parcel;
    private int viewCount;
    @OneToMany(
            mappedBy = "sellArticle",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Photo> photo = new ArrayList<>();


    @ManyToOne
    private SiteUser author;

    @OneToMany( mappedBy = "sellArticle",
            cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Answer> answers;

    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;

    // Board에서 파일 처리 위함
    public void addPhoto(Photo photo) {
        this.photo.add(photo);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(photo.getSellArticle() != this)
            // 파일 저장
            photo.setSellArticle(this);
    }
    public void increaseViewCount() {
        this.viewCount++;
    }
}
