package com.ssafy.waple.vote.exception;

import com.ssafy.waple.error.exception.DuplicateKeyException;

public class DuplicatePromisePlaceException extends DuplicateKeyException {
	public DuplicatePromisePlaceException(int groupId, int promiseId, String placeId) {
		super("PromisePlace " + groupId + "-" + promiseId + "-" + placeId + " is already exist");
	}
}
