package com.ssafy.waple.user.exception;

import com.ssafy.waple.error.exception.BusinessException;
import com.ssafy.waple.error.exception.ErrorCode;

public class UserOwnerException extends BusinessException {
	public UserOwnerException(int groupId) {
		super(groupId + "'s owner", ErrorCode.OWNER_NOT_ALLOW);
	}
}
