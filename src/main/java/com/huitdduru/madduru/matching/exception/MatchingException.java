package com.huitdduru.madduru.matching.exception;

import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorCode;
import lombok.Getter;

@Getter
public class MatchingException extends BaseException {

    private final String message;

    public MatchingException(String message) {
        super(ErrorCode.MATCHING_EXCEPTION);
        this.message = message;
    }
}
