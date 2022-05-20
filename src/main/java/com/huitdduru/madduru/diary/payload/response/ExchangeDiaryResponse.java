package com.huitdduru.madduru.diary.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ExchangeDiaryResponse {

    private final Integer id;

    private final String title;

    private final String writer;

    private final String date;

    private final LocalDateTime createdAt;

}
