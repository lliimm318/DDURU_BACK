package com.huitdduru.madduru.diary.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailListResponse {

    private String matchedUserName;
    private List<DetailResponse> list;

}
