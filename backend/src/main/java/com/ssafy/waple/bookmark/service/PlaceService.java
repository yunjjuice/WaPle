package com.ssafy.waple.bookmark.service;

import com.ssafy.waple.bookmark.dto.PlaceDto;

public interface PlaceService {
	// 장소 상세 조회
	PlaceDto read(String placeId);

	// 장소 추가
	boolean create(PlaceDto placeData);

	// 장소 업데이트
	boolean update(PlaceDto placeData);
}
