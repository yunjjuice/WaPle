package com.ssafy.waple.theme.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.waple.error.exception.InvalidValueException;
import com.ssafy.waple.group.dto.GroupDto;
import com.ssafy.waple.group.exception.GroupNotFoundException;
import com.ssafy.waple.group.service.GroupService;
import com.ssafy.waple.theme.controller.ThemeController;
import com.ssafy.waple.theme.dao.ThemeDao;
import com.ssafy.waple.theme.dto.ThemeDto;
import com.ssafy.waple.theme.dto.ThemeGroupAll;
import com.ssafy.waple.theme.dto.ThemeGroupPlace;
import com.ssafy.waple.theme.exception.DuplicatedThemeException;
import com.ssafy.waple.theme.exception.ThemeNotFoundException;
import com.ssafy.waple.theme.exception.UserNotInGroupException;

@Service
public class ThemeServiceImpl implements ThemeService {

	private static final String PRIMARY_KEY_CONSTRAINT_MSG = "for key 'PRIMARY'";
	private static final String GROUP_FOREIGN_KEY_CONSTRAINT_MSG = " a foreign key constraint fails (`WAPLE`.`THEMES`, CONSTRAINT `FK_GROUPS_THEMES` FOREIGN KEY (`GROUP_ID`) REFERENCES `GROUPS` (`GROUP_ID`)";
	private static final String PRIMARY_KEY_NULL_MSG = "'THEME_ID' cannot be null";

	@Autowired
	ThemeDao dao;

	// DB 연결 안될시 나는 에러명 Could not connect to address (나중에 AOP에서 처리)

	// 테마 생성
	@Override
	public void create(String token, int groupId, ThemeDto theme) {

		// AOP를 이용해서 그룹 아이디가 존재하는것인지 체크 할 필요가 있을듯!

		if(theme.getName() == null || theme.getName() == "" || theme.getIcon() == null || theme.getIcon() == "") {
			throw new InvalidValueException("Name or Icon is not null or Empty");
		}
		try {
			dao.create(theme);
		} catch (DataAccessException e) {
			if(e.getMessage().contains(PRIMARY_KEY_CONSTRAINT_MSG)) {
				throw new DuplicatedThemeException(groupId, theme.getThemeId());
			}
			if(e.getMessage().contains(GROUP_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new GroupNotFoundException(groupId);
			}
			throw e;
		}
	}

	@Override
	public List<ThemeDto> read(String token, int groupId) {

		return dao.read(groupId);
	}

	@Override
	public void update(String token, ThemeDto theme) {

		int result = dao.update(theme);

		if (result < 1) {
			throw new ThemeNotFoundException(theme.getGroupId(), theme.getThemeId());
		}

	}

	@Override
	public void delete(String token, int groupId, int themeId) {

		int result = dao.delete(groupId, themeId);

		if (result < 1) {
			throw new ThemeNotFoundException(groupId, themeId);
		}
	}

	// 테마 및 그룹 조회
	@Override
	public List<ThemeGroupAll> readAll(String token, long userId) {
		List<ThemeGroupAll> list = dao.readAll(userId);
		int size = list.size();
		for(int i=0; i<size; i++) {
			ThemeGroupAll dto = list.get(i);
			dto.setThemeIds(Arrays.stream(dto.getThemes().split(",")).map(Integer::parseInt)
				.collect(Collectors.toList()));
			dto.setThemeNames(Arrays.stream(dto.getThemeName().split(",")).collect(Collectors.toList()));
			dto.setGroupNames(Arrays.stream(dto.getGroupName().split(",")).collect(Collectors.toList()));
		}
		return list;
	}

	@Override
	public List<ThemeGroupPlace> readAll(String token, long userId, String placeId) {
		List<ThemeGroupPlace> list = dao.readAllByPlace(userId, placeId);
		return list;
	}

	@Override
	public List<ThemeGroupPlace> readAllById(String token, long userId) {
		return dao.readAllById(userId);
	}
}
