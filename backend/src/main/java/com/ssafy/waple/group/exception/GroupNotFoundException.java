package com.ssafy.waple.group.exception;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class GroupNotFoundException extends EntityNotFoundException {
	public GroupNotFoundException(int groupId) {
		super("Group " + groupId + " is not found");
	}
}
