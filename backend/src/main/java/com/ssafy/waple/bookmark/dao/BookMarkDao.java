package com.ssafy.waple.bookmark.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.waple.bookmark.dto.BookMarkDto;
import com.ssafy.waple.bookmark.dto.SearchType;

@Mapper
public interface BookMarkDao {
	// 북마크 생성
	int create(BookMarkDto bookMark) throws Exception;

	// 북마크 조회
	List<BookMarkDto> read(SearchType.Group group);

	//북마크 삭제
	int delete(@Param("userId") long userId, @Param("themeId") int themeId, @Param("groupId") int groupId,
		@Param("placeId") String placeId);
}
