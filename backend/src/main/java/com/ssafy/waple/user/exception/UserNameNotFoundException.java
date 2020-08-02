package com.ssafy.waple.user.exception;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class UserNameNotFoundException extends EntityNotFoundException {
	public UserNameNotFoundException() {
		super("Nickname Can't be Null or Empty");
	}
}
