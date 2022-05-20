package com.huitdduru.madduru.diary.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CalendarResponse {

    private final Integer id;

    private final String title;

    private final String date;

    private final String writer;

    private final String imageUrl;

    private final Boolean isMine;

}
