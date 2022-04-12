package com.huitdduru.madduru.email.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RandomRequest {

    private String email;

    private String code;

}
