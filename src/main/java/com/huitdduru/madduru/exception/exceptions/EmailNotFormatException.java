package com.huitdduru.madduru.exception.exceptions;

import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorCode;

public class EmailNotFormatException extends BaseException {
    public EmailNotFormatException() {
        super(ErrorCode.EMAIL_NOT_FORMAT);
    }
}
