package com.huitdduru.madduru.exception.exceptions;

import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorCode;

public class UserAlreadyException extends BaseException {
    public UserAlreadyException() {
        super(ErrorCode.USER_ALREADY);
    }
}
