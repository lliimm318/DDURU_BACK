package com.huitdduru.madduru.diary.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class DiaryRequest {

    private final String title;

    private final String feeling;

    private final LocalDate date;

    private final String contents;

    private final String imageUrl;

}
