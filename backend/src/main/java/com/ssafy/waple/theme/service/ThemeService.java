package com.ssafy.waple.theme.service;

import java.util.List;
import java.util.Map;

import com.ssafy.waple.theme.controller.ThemeController;
import com.ssafy.waple.theme.dto.ThemeDto;
import com.ssafy.waple.theme.dto.ThemeGroupAll;
import com.ssafy.waple.theme.dto.ThemeGroupPlace;

public interface ThemeService {

	// 테마 생성
	void create(String token, int groupId, ThemeDto theme);

	// 테마 조회
	List<ThemeDto> read(String token, int groupId);

	// 테마 수정
	void update(String token, int groupId, int themeId, ThemeDto theme);

	// 테마 삭제
	void delete(String token, int groupId, int themeId);

	// 테마 및 그룹 조회
	List<ThemeGroupAll> readAll(String token, long userId);

	// 테마 및 그룹 조회
	List<ThemeGroupPlace> readAll(String token, long userId, String placeId);

	List<ThemeGroupPlace> readAllById(String token, long userId);
}
