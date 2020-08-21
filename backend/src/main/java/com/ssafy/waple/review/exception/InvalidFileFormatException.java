package com.ssafy.waple.review.exception;

import com.ssafy.waple.error.exception.IncorrectFormatException;

public class InvalidFileFormatException extends IncorrectFormatException {
	public InvalidFileFormatException() {
		super("Image file's format is not accepted.");
	}
}
