package com.huitdduru.madduru.diary.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DiaryResponse {
    private final Integer diaryId;

    private final String currentUserImg;

    private final String mateImg;

    private final Boolean isMyTurn;

    private final String mateName;

    private final Long hoursAgo;
}
