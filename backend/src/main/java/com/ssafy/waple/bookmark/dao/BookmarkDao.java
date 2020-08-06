package com.ssafy.waple.bookmark.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.waple.bookmark.dto.BookmarkDto;
import com.ssafy.waple.bookmark.dto.SearchType;

@Mapper
public interface BookmarkDao {
	// 북마크 생성
	int create(BookmarkDto bookmark);

	// 북마크 조회
	List<BookmarkDto> read(SearchType.Group group);

	//북마크 삭제
	int delete(@Param("themeId") int themeId, @Param("groupId") int groupId, @Param("placeId") String placeId);

	// 북마크 전체 조회
	List<BookmarkDto> readAll(@Param("userId") long userId, @Param("limit") int limit, @Param("offset") int offset);
}
