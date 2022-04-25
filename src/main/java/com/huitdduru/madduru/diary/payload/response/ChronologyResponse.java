package com.huitdduru.madduru.diary.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChronologyResponse {

    private Integer id;
    private LocalDateTime date;
    private String title;
    private String opponent;

}
