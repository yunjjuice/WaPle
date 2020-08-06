package com.ssafy.waple.theme.service;

import java.util.List;

import com.ssafy.waple.theme.dto.ThemeDto;

public interface ThemeService {

	// 테마 생성
	void create(String token, int groupId, ThemeDto theme);

	// 테마 조회
	List<ThemeDto> readAll(String token, int groupId);

	// 테마 수정
	void update(String token, int groupId, int themeId, ThemeDto theme);

	// 테마 삭제
	void delete(String token, int groupId, int themeId);
}
