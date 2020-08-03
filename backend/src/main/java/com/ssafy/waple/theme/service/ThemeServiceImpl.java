package com.ssafy.waple.theme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.waple.group.dto.GroupDto;
import com.ssafy.waple.group.service.GroupService;
import com.ssafy.waple.theme.dao.ThemeDao;
import com.ssafy.waple.theme.dto.ThemeDto;
import com.ssafy.waple.theme.exception.ThemeNotFoundException;
import com.ssafy.waple.theme.exception.UserNotInGroupException;

@Service
public class ThemeServiceImpl implements ThemeService {
	@Autowired
	ThemeDao dao;

	// DB 연결 안될시 나는 에러명 Could not connect to address (나중에 AOP에서 처리)

	// 테마 생성
	@Override
	public void create(String token, int groupId, ThemeDto theme) {

		// AOP를 이용해서 그룹 아이디가 존재하는것인지 체크 할 필요가 있을듯!

		try {
			// 1. 테마 이름 정규식을 통해서 유효성 검사
			// 2. 테마 아이콘 역시 유효성 검사 필요
			dao.create(theme);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ThemeDto> read(String token, int groupId) {

		return dao.read(groupId);
	}

	@Override
	public void update(String token, int groupId, int themeId, ThemeDto theme) {

		int result = dao.update(theme);

		if (result < 1) {
			throw new ThemeNotFoundException(groupId, themeId);
		}

	}

	@Override
	public void delete(String token, int groupId, int themeId) {

		int result = dao.delete(groupId, themeId);

		if (result < 1) {
			throw new ThemeNotFoundException(groupId, themeId);
		}
	}
}
