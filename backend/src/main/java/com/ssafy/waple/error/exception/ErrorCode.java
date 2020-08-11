package com.ssafy.waple.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
	// Common
	INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
	DUPLICATED_KEY(400, "C002", "Duplicated Key"),
	ENTITY_NOT_FOUND(400, "C003", "Entity Not Found"),
	INVALID_TYPE_VALUE(400, "C005", "Invalid Type Value"),
	HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
	NOT_FOUND(404, "C004", "Not Found"),
	METHOD_NOT_ALLOWED(405, "C007", "Invalid Input Value"),

	//Upload image
	FILE_SAVE_FAILED(404, "F001", "Failed to save file");

	private final String code;
	private final String message;
	private final int status;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return code;
	}

	public int getStatus() {
		return status;
	}
}
