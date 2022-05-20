package com.huitdduru.madduru.diary.payload.response;

import lombok.*;

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
    private String date;
    private String writer;

}
