package com.ssafy.waple.review.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.waple.review.dto.ReviewDto;

@Mapper
public interface ReviewDao {

	// 리뷰 생성
	int create(ReviewDto dto);

}
