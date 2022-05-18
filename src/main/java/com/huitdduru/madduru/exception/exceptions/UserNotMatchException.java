package com.huitdduru.madduru.exception.exceptions;

import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorCode;

public class UserNotMatchException extends BaseException {
    public UserNotMatchException() {
        super(ErrorCode.USER_NOT_MATCHED);
    }
}
