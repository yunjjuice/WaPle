package com.ssafy.waple.bookmark.service;

import java.util.List;

import com.ssafy.waple.bookmark.dto.BookMarkDto;
import com.ssafy.waple.bookmark.dto.SearchType;

public interface BookMarkService {
	// 북마크 생성
	boolean create(BookMarkDto bookMark);

	// 북마크 조회
	List<BookMarkDto> read(SearchType type);

	// 북마크 삭제
	boolean delete(long userId, int themeId, int groupId, String placeId);
}
