package com.techno_market.techno_market.domain.user.dto;

import com.techno_market.techno_market.domain.user.entity.SiteUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class UserDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String username;
    private String nickName;
    private List<String> authorities;

    public UserDto(SiteUser user) {
        this.id = user.getId();
        this.createDate = user.getCreateDate();
        this.modifyDate = user.getModifyDate();
        this.username = user.getUsername();
        this.nickName = user.getNickName();
        this.authorities = user.getAuthoritiesAsStringList();
    }
}
