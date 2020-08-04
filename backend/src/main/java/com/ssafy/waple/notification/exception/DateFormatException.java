package com.ssafy.waple.notification.exception;

import com.ssafy.waple.error.exception.ErrorCode;
import com.ssafy.waple.error.exception.InvalidValueException;

public class DateFormatException extends InvalidValueException {
	public DateFormatException() {
		super("Incorrect Data Format", ErrorCode.INVALID_INPUT_VALUE);
	}

}
