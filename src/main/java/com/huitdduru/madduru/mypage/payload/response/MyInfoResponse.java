package com.huitdduru.madduru.mypage.payload.response;

import com.huitdduru.madduru.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MyInfoResponse {
    private final String name;

    private final String intro;

    private final String img;

    public static MyInfoResponse of(User user) {
        return MyInfoResponse.builder()
                .name(user.getName())
                .intro(user.getIntro())
                .img(user.getImagePath())
                .build();
    }
}
