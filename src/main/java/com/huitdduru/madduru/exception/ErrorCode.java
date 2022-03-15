package com.huitdduru.madduru.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN(401, "invalid token"),
    USER_NOT_FOUND(404,"user not found"),
    USER_ALREADY(409, "user already");

    private final int status;

    private final String message;

}
