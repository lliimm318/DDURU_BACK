package com.huitdduru.madduru.diary.payload.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChronologyResponse {

    private Integer id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String opponent;
    private List<DiaryDetailResponse> diaries;

}
