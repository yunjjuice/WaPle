package com.ssafy.waple.group.exception;

import com.ssafy.waple.error.exception.DuplicateKeyException;

public class DuplicateMemberException extends DuplicateKeyException {
	public DuplicateMemberException(int groupId, long userId) {
		super("Member " + groupId + "-" + userId + " is already exist");
	}
}
