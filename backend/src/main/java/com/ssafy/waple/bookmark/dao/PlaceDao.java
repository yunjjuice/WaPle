package com.ssafy.waple.bookmark.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.waple.bookmark.dto.PlaceDto;

@Mapper
public interface PlaceDao {
	// 장소 상세 조회
	PlaceDto read(String placeId);

	// 장소 추가
	int create(PlaceDto placeData) throws Exception;

	// 장소 수정
	int update(PlaceDto placeData) throws Exception;
}
