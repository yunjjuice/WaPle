package com.ssafy.waple.theme.exception;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class UserNotInGroupException extends EntityNotFoundException {
	public UserNotInGroupException(long userId) {
		super(userId + " Not Belong in Group");
	}
}
