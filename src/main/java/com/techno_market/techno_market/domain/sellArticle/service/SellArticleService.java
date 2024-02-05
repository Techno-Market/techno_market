package com.techno_market.techno_market.domain.sellArticle.service;

import com.techno_market.techno_market.domain.photo.entity.Photo;
import com.techno_market.techno_market.domain.photo.repository.PhotoRepository;
import com.techno_market.techno_market.domain.photo.service.FileHandler;
import com.techno_market.techno_market.domain.sellArticle.dto.SellArticleCreateDto;
import com.techno_market.techno_market.domain.sellArticle.entity.CategoryType;
import com.techno_market.techno_market.domain.sellArticle.entity.SellArticle;
import com.techno_market.techno_market.domain.sellArticle.repository.SellArticleRepository;
import com.techno_market.techno_market.domain.user.entity.SiteUser;
import com.techno_market.techno_market.domain.user.repository.UserRepository;
import com.techno_market.techno_market.global.rsData.RsData;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellArticleService {
    private final SellArticleRepository sellArticleRepository;
    private final PhotoRepository photoRepository;
    private final FileHandler fileHandler;
    private final UserRepository userRepository;

    @Transactional
    public RsData<SellArticleCreateDto> create(String subject, String content, int price, String area, String category, Boolean directly,
                                               Boolean parcel, List<MultipartFile> postImage, String username) throws Exception {
        // 사용자 정보를 통해 사용자 엔터티를 찾거나 새로 생성하여 저장
        SiteUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        SellArticle a = new SellArticle();
        a.setSubject(subject);
        a.setContent(content);
        a.setPrice(price);
        a.setArea(area);
        a.setCategory(category);
        a.setDirectly(directly);
        a.setParcel(parcel);
        a.setCreateDate(LocalDateTime.now());

        // 현재 로그인한 사용자와 게시물을 연결
        a.setAuthor(user);

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

        return RsData.of("2", "게시물이 생성되었습니다.", sellArticleCreateDto);
    }

    public boolean isCurrentUserAuthorized(Long sellArticleId, String currentUsername) {
        SellArticle sellArticle = sellArticleRepository.findById(sellArticleId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 게시물을 찾을 수 없습니다."));

        return sellArticle.getAuthor().getUsername().equals(currentUsername);
    }

    public Page<SellArticle> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.sellArticleRepository.findAll(pageable);
    }

    @Transactional
    public SellArticle getArticle(Long id) {
        SellArticle sellArticle = this.sellArticleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

        // 조회수 증가
        sellArticle.increaseViewCount();

        return sellArticle;
    }

    public RsData<SellArticle> modify(Long sellArticleId, String subject, String content, int price, String area, String category,
                                      Boolean directly, Boolean parcel, List<MultipartFile> postImage) throws  Exception {
        SellArticle sellArticle = sellArticleRepository.findById(sellArticleId)
                .orElseThrow(() -> new Exception("해당 ID의 게시물을 찾을 수 없습니다."));

        sellArticle.setSubject(subject);
        sellArticle.setContent(content);
        sellArticle.setPrice(price);
        sellArticle.setArea(area);
        sellArticle.setCategory(category);
        sellArticle.setDirectly(directly);
        sellArticle.setParcel(parcel);

        List<Photo> photoList = fileHandler.parseFileInfo(postImage);

        if (!photoList.isEmpty()) {
            for (Photo photo : photoList) {
                sellArticle.addPhoto(photoRepository.save(photo));
            }
        }
        sellArticleRepository.save(sellArticle);

        return RsData.of("3", "게시물이 수정되었습니다.", sellArticle);
    }

    public RsData<SellArticle> delete(SellArticle sellArticle) {
        this.sellArticleRepository.delete(sellArticle);
        return RsData.of("3", "게시물이 삭제되었습니다.", sellArticle);
    }
    public Page<SellArticle> getSearchList(String kw, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<SellArticle> spec = search(kw);
        return this.sellArticleRepository.findAll(spec, pageable);
    }
    private Specification<SellArticle> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<SellArticle> a, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                return cb.or(
                        cb.like(a.get("subject"), "%" + kw + "%"),
                        cb.like(a.get("area"), "%" + kw + "%")
                );
            }
        };
    }
    public Page<SellArticle> getHighPriceArticles(String kw, int page, CategoryType categoryType) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("price"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<SellArticle> spec = searchCategory(kw, categoryType);
        return this.sellArticleRepository.findAll(spec, pageable);
    }

    public Page<SellArticle> getLowPriceArticles(String kw, int page, CategoryType categoryType) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("price"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<SellArticle> spec = searchCategory(kw, categoryType);
        return this.sellArticleRepository.findAll(spec, pageable);
    }

    public Page<SellArticle> getCategoryList(int page, CategoryType categoryType) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<SellArticle> spec = categoryType(categoryType);
        return this.sellArticleRepository.findAll(spec, pageable);
    }
    private Specification<SellArticle> categoryType(CategoryType categoryType) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<SellArticle> a, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);

                if(categoryType == CategoryType.LAPTOP) {
                    return cb.or(
                            cb.like(a.get("category"), "%" + "노트북" + "%")
                    );
                } else if(categoryType == CategoryType.MOBILE) {
                    return cb.or(
                            cb.like(a.get("category"), "%" + "핸드폰" + "%")
                    );
                } else if(categoryType == CategoryType.MONITOR) {
                    return cb.or(
                            cb.like(a.get("category"), "%" + "모니터" + "%")
                    );
                } else if(categoryType == CategoryType.EARPHONE) {
                    return cb.or(
                            cb.like(a.get("category"), "%" + "이어폰" + "%")
                    );
                } else if(categoryType == CategoryType.SMARTWATCH) {
                    return cb.or(
                            cb.like(a.get("category"), "%" + "스마트워치" + "%")
                    );
                } else if(categoryType == CategoryType.TABLET) {
                    return cb.or(
                            cb.like(a.get("category"), "%" + "테블릿" + "%")
                    );
                }
                return cb.conjunction();
            }
        };
    }
    private Specification<SellArticle> searchCategory(String kw, CategoryType categoryType) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<SellArticle> a, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);

                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.isNotBlank(kw) || categoryType == CategoryType.ALL) {
                    predicates.add(cb.or(
                            cb.like(a.get("subject"), "%" + kw + "%"),
                            cb.like(a.get("area"), "%" + kw + "%")
                    ));
                }

                if (categoryType != null) {
                    switch (categoryType) {
                        case LAPTOP:
                            predicates.add(cb.or(
                                    cb.like(a.get("category"), "%" + "노트북" + "%")
                            ));
                            break;
                        case MOBILE:
                            predicates.add(cb.or(
                                    cb.like(a.get("category"), "%" + "핸드폰" + "%")
                            ));
                            break;
                        case MONITOR:
                            predicates.add(cb.or(
                                    cb.like(a.get("category"), "%" + "모니터" + "%")
                            ));
                            break;
                        case EARPHONE:
                            predicates.add(cb.or(
                                    cb.like(a.get("category"), "%" + "이어폰" + "%")
                            ));
                            break;
                        case SMARTWATCH:
                            predicates.add(cb.or(
                                    cb.like(a.get("category"), "%" + "스마트워치" + "%")
                            ));
                            break;
                        case TABLET:
                            predicates.add(cb.or(
                                    cb.like(a.get("category"), "%" + "테블릿" + "%")
                            ));
                            break;
                    }
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
