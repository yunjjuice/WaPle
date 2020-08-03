package com.ssafy.waple.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.waple.bookmark.dto.SearchType;
import com.ssafy.waple.review.dto.ReviewDto;

@Mapper
public interface ReviewDao {

	// 리뷰 생성
	int create(ReviewDto dto);

	// 리뷰 조회
	List<ReviewDto> readAll(@Param("groupId") int groupId, @Param("placeId") String placeId);

}
