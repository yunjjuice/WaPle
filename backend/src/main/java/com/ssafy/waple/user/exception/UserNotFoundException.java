package com.ssafy.waple.user.exception;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
	public UserNotFoundException(long userId) {
		super(userId + "is not found");
	}
}
