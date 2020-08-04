package com.ssafy.waple.bookmark.exception;

import com.ssafy.waple.error.exception.BusinessException;
import com.ssafy.waple.error.exception.ErrorCode;

public class ThemeIsNullException extends BusinessException {
	public ThemeIsNullException() {
		super("Theme Can't be Null", ErrorCode.INVALID_INPUT_VALUE);
	}
}
