package com.ssafy.waple.group.exception;

import com.ssafy.waple.error.exception.DuplicateKeyException;

public class DuplicatedMemberException extends DuplicateKeyException {
	public DuplicatedMemberException(int groupId, long userId) {
		super("member(groupId: " + groupId + ", userId: " + userId + ") is duplicated");
	}
}
