package com.ssafy.waple.theme.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.swagger.models.auth.In;

import com.ssafy.waple.theme.controller.ThemeController;
import com.ssafy.waple.theme.dto.ThemeDto;
import com.ssafy.waple.theme.dto.ThemeGroupAll;

@Mapper
public interface ThemeDao {

	// 테마 생성
	int create(ThemeDto theme);

	// 테마 조회
	List<ThemeDto> read(int groupId);

	// 테마 수정
	int update(ThemeDto theme);

	// 테마 삭제
	int delete(@Param("groupId") int groupId, @Param("themeId") int themeId);

	// 테마 및 그룹 조회
	List<ThemeGroupAll> readAll(long userId);
}
