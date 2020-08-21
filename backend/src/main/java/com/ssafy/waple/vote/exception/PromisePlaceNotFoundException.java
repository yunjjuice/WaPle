package com.ssafy.waple.vote.exception;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class PromisePlaceNotFoundException extends EntityNotFoundException {
	public PromisePlaceNotFoundException(int groupId, int promiseId, String placeId) {
		super("PromisePlace " + groupId + "-" + promiseId + "-" + placeId + " is not found");
	}
}
