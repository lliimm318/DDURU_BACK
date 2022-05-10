package com.huitdduru.madduru.diary.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryListResponse {

    private Integer id;
    private ExchangeDiaryResponse diary;
    private Boolean isMine;

}
