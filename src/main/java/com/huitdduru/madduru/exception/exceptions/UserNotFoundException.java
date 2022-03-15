package com.huitdduru.madduru.exception.exceptions;

import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorCode;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
