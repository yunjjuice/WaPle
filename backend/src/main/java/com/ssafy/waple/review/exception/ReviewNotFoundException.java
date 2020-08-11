package com.ssafy.waple.review.exception;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class ReviewNotFoundException extends EntityNotFoundException {
	public ReviewNotFoundException(int reviewId) {
		super(reviewId + " is Not Exist");
	}
}
