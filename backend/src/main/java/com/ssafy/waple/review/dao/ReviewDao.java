package com.ssafy.waple.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.waple.bookmark.dto.SearchType;
import com.ssafy.waple.review.dto.ReviewDto;
import com.ssafy.waple.review.dto.ReviewPlaceDto;

@Mapper
public interface ReviewDao {

	// 리뷰 생성
	int create(ReviewDto dto);

	// 장소 리뷰 리스트 조회
	List<ReviewDto> readAll(@Param("userId")long userId, @Param("placeId") String placeId);

	// 리뷰 장소 리스트 조회
	List<ReviewPlaceDto> readAllByUserId(long userId);

	// 리뷰 상세 조회
	ReviewDto read(int reviewId);

	// 리뷰 업데이트
	int update(@Param("reviewId")int reviewId, @Param("title")String title ,@Param("content")String content,
		@Param("media")String media);

	// 리뷰 삭제
	int delete(int reviewId);
}
