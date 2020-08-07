package com.ssafy.waple.review.service;

import java.util.List;

import com.ssafy.waple.bookmark.dto.SearchType;
import com.ssafy.waple.review.dto.ReviewDto;
import com.ssafy.waple.review.dto.ReviewPlaceDto;

public interface ReviewService {

	// 리뷰 생성
	void create(String token, ReviewDto dto);

	// 장소의 리뷰 리스트 조회
	List<ReviewDto> readAll(String token, long userId, String placeId, int limit, int offset);

	// 리뷰의 장소 리스트 조회
	List<ReviewPlaceDto> readAllByUserId(String token, long userId, int limit, int offset);

	// 리뷰 상세 조회
	ReviewDto read(String token, int reviewId);

	// 리뷰 업데이트
	void update(String token, int reviewId, String title ,String content, String media);

	// 리뷰 삭제
	void delete(String token, int reviewId);
}
