package com.ssafy.waple.theme.service;

import java.util.List;

import com.ssafy.waple.theme.dto.ThemeDto;

public interface ThemeService {
	// 테마 갯수 조회
	int readLastId(int groupId);

	// 테마 생성
	boolean create(long userId, ThemeDto theme);

	// 테마 조회
	List<ThemeDto> read(long userId, int groupId);

	// 테마 수정
	void update(long userId, int groupId, int themeId, ThemeDto theme);

	// 테마 삭제
	void delete(long userId, int groupId, int themeId);
}
