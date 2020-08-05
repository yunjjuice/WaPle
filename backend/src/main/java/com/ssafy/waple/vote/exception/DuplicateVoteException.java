package com.ssafy.waple.vote.exception;

import com.ssafy.waple.error.exception.DuplicateKeyException;

public class DuplicateVoteException extends DuplicateKeyException {
	public DuplicateVoteException(int groupId, int promiseId, String placeId, long userId) {
		super("PromisePlace " + groupId + "-" + promiseId + "-" + placeId + "-" + userId + " is already exist");
	}
}
