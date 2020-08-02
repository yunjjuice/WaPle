package com.ssafy.waple.error.exception;

public class DuplicateKeyException extends BusinessException {
	public DuplicateKeyException(String message) {
		super(message, ErrorCode.DUPLICATED_KEY);
	}
}
