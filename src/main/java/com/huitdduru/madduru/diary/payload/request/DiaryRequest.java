package com.huitdduru.madduru.diary.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DiaryRequest {

    private final String title;

    private final String feeling;

    private final String date;

    private final String contents;

    private final String imageUrl;

}
