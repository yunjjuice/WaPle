package com.ssafy.waple.promise.exception;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class PromiseNotFoundException extends EntityNotFoundException {
	public PromiseNotFoundException(int groupId, int promiseId) {
		super("Promise (group " + groupId + ", promise " + promiseId + ") is not found.");
	}
}
