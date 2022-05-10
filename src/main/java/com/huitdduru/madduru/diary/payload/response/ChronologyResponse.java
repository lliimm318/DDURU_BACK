package com.huitdduru.madduru.diary.payload.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChronologyResponse {

    private Integer id;
    private DiaryListResponse diary;
    private Boolean isMine;

}
