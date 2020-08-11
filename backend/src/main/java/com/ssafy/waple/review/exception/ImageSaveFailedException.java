package com.ssafy.waple.review.exception;

import com.ssafy.waple.error.exception.BusinessException;
import com.ssafy.waple.error.exception.ErrorCode;

public class ImageSaveFailedException extends BusinessException {
	public ImageSaveFailedException() {
		super("Image save failed. Please check file route.", ErrorCode.FILE_SAVE_FAILED);
	}
}
