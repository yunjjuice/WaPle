package com.ssafy.waple.review.service;

import com.ssafy.waple.review.dto.ReviewDto;

public interface ReviewService {

	// 리뷰 생성
	void create(ReviewDto dto);
}
