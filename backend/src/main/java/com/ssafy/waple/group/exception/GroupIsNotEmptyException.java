package com.ssafy.waple.group.exception;

import com.ssafy.waple.error.exception.BusinessException;
import com.ssafy.waple.error.exception.ErrorCode;

public class GroupIsNotEmptyException extends BusinessException {
	public GroupIsNotEmptyException(int groupId) {
		super("Group " + groupId + " is not empty", ErrorCode.GROUP_IS_NOT_EMPTY);
	}
}
