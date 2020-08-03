package com.ssafy.waple.review.error;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class ReviewNotFoundException extends EntityNotFoundException {
	public ReviewNotFoundException(int reviewId) {
		super(reviewId + " is Not Exist");
	}
}
