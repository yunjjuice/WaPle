package com.ssafy.waple.theme.exception;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class ThemeNotFoundException extends EntityNotFoundException {
	public ThemeNotFoundException(int group, int theme) {
		super(group + "'s " + theme + " Not Founded");
	}
}
