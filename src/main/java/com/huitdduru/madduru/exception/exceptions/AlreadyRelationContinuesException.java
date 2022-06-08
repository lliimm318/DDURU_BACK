package com.huitdduru.madduru.exception.exceptions;

import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorCode;

public class AlreadyRelationContinuesException extends BaseException {
    public AlreadyRelationContinuesException() {
        super(ErrorCode.ALREADY_RELATIONS_CONTINUES);
    }
}
