package com.huitdduru.madduru.exception.exceptions;

import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorCode;

public class NotEmailFormatException extends BaseException {
    public NotEmailFormatException() {
        super(ErrorCode.NOT_MAIL_FORMAT);
    }
}
