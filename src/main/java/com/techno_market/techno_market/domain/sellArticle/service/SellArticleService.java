package com.techno_market.techno_market.domain.sellArticle.service;

import com.techno_market.techno_market.domain.photo.entity.Photo;
import com.techno_market.techno_market.domain.photo.repository.PhotoRepository;
import com.techno_market.techno_market.domain.photo.service.FileHandler;
import com.techno_market.techno_market.domain.sellArticle.dto.SellArticleCreateDto;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.sellArticle.repository.SellArticleRepository;
import com.techno_market.techno_market.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellArticleService {
    private final SellArticleRepository sellArticleRepository;
    private final PhotoRepository photoRepository;
    private final FileHandler fileHandler;

    @Transactional
    public RsData<SellArticleCreateDto> create(String subject, String content, int price, String area, String category, Boolean directly,
                                               Boolean parcel, List<MultipartFile> postImage) throws Exception {
        SellArticle a = new SellArticle();
        a.setSubject(subject);
        a.setContent(content);
        a.setPrice(price);
        a.setArea(area);
        a.setCategory(category);
        a.setDirectly(directly);
        a.setParcel(parcel);
        a.setCreateDate(LocalDateTime.now());

        List<Photo> photoList = fileHandler.parseFileInfo(postImage);

        if (!photoList.isEmpty()) {
            for (Photo photo : photoList) {
                a.addPhoto(photoRepository.save(photo));
            }
        }
        this.sellArticleRepository.save(a).getId();

        // SellArticleDto로 변환
        SellArticleCreateDto sellArticleCreateDto = new SellArticleCreateDto();
        sellArticleCreateDto.setSubject(a.getSubject());
        sellArticleCreateDto.setContent(a.getContent());
        sellArticleCreateDto.setPrice(a.getPrice());
        sellArticleCreateDto.setArea(a.getArea());
        sellArticleCreateDto.setCategory(a.getCategory());
        sellArticleCreateDto.setDirectly(a.getDirectly());
        sellArticleCreateDto.setParcel(a.getParcel());

        return RsData.of("S-2", "게시물이 생성되었습니다.", sellArticleCreateDto);
    }

    public List<SellArticle> getList() {
        return this.sellArticleRepository.findAll();
    }

    @Transactional
    public SellArticle getArticle(Long id) {
        SellArticle sellArticle = this.sellArticleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

        // 조회수 증가
        sellArticle.increaseViewCount();

        return sellArticle;
    }

    public RsData<SellArticle> modify(SellArticle sellArticle, String subject, String content, int price, String area) {
        sellArticle.setSubject(subject);
        sellArticle.setContent(content);
        sellArticle.setPrice(price);
        sellArticle.setArea(area);

        sellArticleRepository.save(sellArticle);

        return RsData.of("S-3", "게시물이 수정되었습니다.", sellArticle);
    }

    public RsData<SellArticle> delete(SellArticle sellArticle) {
        this.sellArticleRepository.delete(sellArticle);
        return RsData.of("S-3", "게시물이 삭제되었습니다.", sellArticle);

    }
}
