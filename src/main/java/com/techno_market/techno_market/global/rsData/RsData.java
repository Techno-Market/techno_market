package com.techno_market.techno_market.global.rsData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techno_market.techno_market.domain.wishList.controller.WishListController;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class RsData<T> {
    private String resultCode;
    private int statusCode;
    private String msg;
    private T data;

    public static <T> RsData<T> of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static <T> RsData<T> of(String resultCode, String msg, T data) {
        int statusCode = Integer.parseInt(resultCode.split("-", 2)[0]);

        return RsData.<T>builder()
                .resultCode(resultCode)
                .statusCode(statusCode)
                .msg(msg)
                .data(data)
                .build();
    }
}

