package com.ssafy.waple.notification.exception;

import com.ssafy.waple.error.exception.ErrorCode;
import com.ssafy.waple.error.exception.InvalidValueException;

public class GroupIdInvaildException extends InvalidValueException {
	public GroupIdInvaildException() {
		super("Group ID can not be default value", ErrorCode.INVALID_INPUT_VALUE);
	}
}
