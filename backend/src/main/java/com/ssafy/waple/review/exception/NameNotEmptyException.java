package com.ssafy.waple.review.exception;

import com.ssafy.waple.error.exception.BusinessException;
import com.ssafy.waple.error.exception.ErrorCode;

public class NameNotEmptyException extends BusinessException {
	public NameNotEmptyException() {
		super("Name can not be empty", ErrorCode.INVALID_INPUT_VALUE);
	}
}
