package com.huitdduru.madduru.matching.message;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserinfoMessage {
    private final String name;
    private final String img;
    private final String intro;
}
