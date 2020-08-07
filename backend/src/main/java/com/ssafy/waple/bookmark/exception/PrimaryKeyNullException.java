package com.ssafy.waple.bookmark.exception;

import com.ssafy.waple.error.exception.BusinessException;
import com.ssafy.waple.error.exception.ErrorCode;

public class PrimaryKeyNullException  extends BusinessException {
	public PrimaryKeyNullException(String message) {
		super(message, ErrorCode.INVALID_INPUT_VALUE);
	}
}
