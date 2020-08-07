package com.ssafy.waple.bookmark.exception;

import com.ssafy.waple.error.exception.EntityNotFoundException;

public class BookmarkNotFonudException extends EntityNotFoundException {
	public BookmarkNotFonudException(int groupId, int themeId, String placeId) {
		super(groupId + " 's " + placeId + " not found in " + themeId);
	}
}
