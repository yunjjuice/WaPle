package com.ssafy.waple.theme.exception;

import com.ssafy.waple.error.exception.DuplicateKeyException;

public class DuplicatedThemeException extends DuplicateKeyException {
	public DuplicatedThemeException(int groupId, int themeId) {
		super(groupId + " and " + themeId + "is exist");
	}
}
