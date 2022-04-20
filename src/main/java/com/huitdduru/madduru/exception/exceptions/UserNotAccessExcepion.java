package com.huitdduru.madduru.exception.exceptions;

import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorCode;

public class UserNotAccessExcepion extends BaseException {
    public UserNotAccessExcepion() {
        super(ErrorCode.USER_NOT_ACCESS);
    }
}
