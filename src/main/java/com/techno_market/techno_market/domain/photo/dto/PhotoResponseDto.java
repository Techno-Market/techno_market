package com.techno_market.techno_market.domain.photo.dto;

import com.techno_market.techno_market.domain.photo.entity.Photo;
import lombok.Getter;

@Getter
public class PhotoResponseDto {
    private Long fileId;  // 파일 id

    public PhotoResponseDto(Photo entity){
        this.fileId = entity.getId();
    }
}

