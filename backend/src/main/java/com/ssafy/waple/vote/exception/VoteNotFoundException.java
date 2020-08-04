package com.ssafy.waple.vote.exception;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class VoteNotFoundException extends EntityNotFoundException {
	public VoteNotFoundException(int groupId, int promiseId, String placeId, long userId) {
		super("Vote " + groupId + "-" + promiseId + "-" + placeId + "-" + userId + " is not found");
	}
}
