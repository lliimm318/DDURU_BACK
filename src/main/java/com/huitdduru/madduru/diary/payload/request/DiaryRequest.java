package com.huitdduru.madduru.diary.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class DiaryRequest {

    private final String title;

    private final String feeling;

    private final LocalDateTime date;

    private final String contents;

}
