package com.ssafy.waple.bookmark.exception;

import com.ssafy.waple.error.exception.BusinessException;
import com.ssafy.waple.error.exception.ErrorCode;

public class GroupIsNullException extends BusinessException {
	public GroupIsNullException() {
		super("Group Can't be Null", ErrorCode.INVALID_INPUT_VALUE);
	}
}
