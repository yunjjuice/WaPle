package com.ssafy.waple.bookmark.error;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class PlaceNotFoundException extends EntityNotFoundException {
	public PlaceNotFoundException(String placeId) {
		super(placeId + " is not exist");
	}
}
