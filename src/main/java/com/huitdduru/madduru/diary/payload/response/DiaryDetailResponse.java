package com.huitdduru.madduru.diary.payload.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDetailResponse {

    private Integer id;
    private String title;
    private String feeling;
    private String image;
    private String content;
    private LocalDate date;
    private String writer;

}
