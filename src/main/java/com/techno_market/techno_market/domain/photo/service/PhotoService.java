package com.techno_market.techno_market.domain.photo.service;

import com.techno_market.techno_market.domain.photo.dto.PhotoDto;
import com.techno_market.techno_market.domain.photo.dto.PhotoResponseDto;
import com.techno_market.techno_market.domain.photo.entity.Photo;
import com.techno_market.techno_market.domain.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;

    //    /**
//     * 이미지 개별 조회
//     */
//    @Transactional(readOnly = true)
//    public PhotoDto findByFileId(Long id){
//
//        Photo entity = photoRepository.findById(id).orElseThrow(()
//                -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));
//
//        PhotoDto photoDto = PhotoDto.builder()
//                .origFileName(entity.getOrigFileName())
//                .filePath(entity.getFilePath())
//                .fileSize(entity.getFileSize())
//                .build();
//
//        return photoDto;
//    }
//
//    /**
//     * 이미지 전체 조회
//     */
//    @Transactional(readOnly = true)
//    public List<PhotoResponseDto> findAllByBoard(Long sellArticleId){
//
//        List<Photo> photoList = photoRepository.findAllBySellArticleId(sellArticleId);
//
//        return photoList.stream()
//                .map(PhotoResponseDto::new)
//                .collect(Collectors.toList());
//    }
    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<Photo> getPhotosBySellArticleId(Long sellArticleId) {
        return photoRepository.findBySellArticleId(sellArticleId);
    }
}
