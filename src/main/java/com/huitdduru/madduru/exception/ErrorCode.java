package com.huitdduru.madduru.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN(401, "invalid token"),
    PASSWORD_NOT_MATCHES(404, "password not matches"),
    USER_NOT_FOUND(404,"user not found"),
    USER_NOT_MATCHED(404,"user not matched"),
    EMAIL_CODE_NOT_FOUND(404,"email code not found"),
    DIARY_NOT_FOUND(404,"diary not found"),
    USER_ALREADY(409, "user already"),
    NOT_MAIL_FORMAT(500, "not email format"),
    USER_NOT_ACCESS(500, "user not access"),
    EMAIL_NOT_FORMAT(500, "not in email format"),
    MAIL_SEND_ERROR(500, "email send error"),
    MATCHING_EXCEPTION(500, "something wrong during matching");

    private final int status;

    private final String message;

}
