package com.huitdduru.madduru.exception.exceptions;

import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorCode;

public class MailSendException extends BaseException {
    public MailSendException() {
        super(ErrorCode.MAIL_SEND_ERROR);
    }
}
