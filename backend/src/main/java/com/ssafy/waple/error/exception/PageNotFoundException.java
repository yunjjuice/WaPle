package com.ssafy.waple.error.exception;

public class PageNotFoundException extends BusinessException{
	public PageNotFoundException() {
		super("Page number has to over than 1", ErrorCode.NOT_FOUND);
	}
}
