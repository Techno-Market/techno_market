package com.techno_market.techno_market.domain.photo.repository;

import com.techno_market.techno_market.domain.photo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findBySellArticleId(Long sellArticleId);
}
