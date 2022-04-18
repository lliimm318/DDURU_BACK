package com.huitdduru.madduru.exception.exceptions;

import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorCode;

public class DiaryNotFoundException extends BaseException {
    public DiaryNotFoundException() {
        super(ErrorCode.DIARY_NOT_FOUND);
    }
}
