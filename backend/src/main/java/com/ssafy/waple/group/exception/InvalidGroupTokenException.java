package com.ssafy.waple.group.exception;

import com.ssafy.waple.error.exception.InvalidValueException;

public class InvalidGroupTokenException extends InvalidValueException {
	public InvalidGroupTokenException() {
		super("Invite token is invalid.");
	}
}
