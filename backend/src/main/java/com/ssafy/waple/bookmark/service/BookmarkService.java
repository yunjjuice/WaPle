package com.ssafy.waple.bookmark.service;

import java.util.List;

import com.ssafy.waple.bookmark.dto.BookmarkDto;
import com.ssafy.waple.bookmark.dto.SearchType;

public interface BookmarkService {
	// 북마크 생성
	void create(String token, BookmarkDto bookMark);

	// 북마크 조회
	List<BookmarkDto> read(String token, SearchType type);

	// 북마크 삭제
	void delete(String token, int themeId, int groupId, String placeId);
}
