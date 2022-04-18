package com.huitdduru.madduru.diary.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class DiaryRequest {

    private final String title;

    private final String feeling;

    private final LocalDateTime date;

    private final String contents;

    private final MultipartFile image;

}
