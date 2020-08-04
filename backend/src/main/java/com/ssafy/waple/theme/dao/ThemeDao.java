package com.ssafy.waple.theme.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.waple.theme.dto.ThemeDto;

@Mapper
public interface ThemeDao {

	// 테마 생성
	int create(ThemeDto theme);

	// 테마 초기 생성
	int createInit(ThemeDto theme);

	// 테마 조회
	List<ThemeDto> read(int groupId);

	// 테마 수정
	int update(ThemeDto theme);

	// 테마 삭제
	int delete(@Param("groupId") int groupId, @Param("themeId") int themeId);
}
