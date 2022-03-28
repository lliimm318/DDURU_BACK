package com.huitdduru.madduru.exception.exceptions;

import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorCode;

public class VerifyNumNotFoundException extends BaseException {
    public VerifyNumNotFoundException() {
        super(ErrorCode.EMAIL_CODE_NOT_FOUND);
    }
}
