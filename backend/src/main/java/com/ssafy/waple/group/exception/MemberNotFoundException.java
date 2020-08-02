package com.ssafy.waple.group.exception;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {
	public MemberNotFoundException(int groupId, long userId) {
		super("(groupId: " + groupId + ", userId: " + userId + ") is not found");
	}
}
