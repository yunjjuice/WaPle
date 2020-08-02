package com.ssafy.waple.theme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	GroupService service;

	// 테마 갯수 조회
	@Override
	public int readLastId(int groupId) {
		return dao.readLastId(groupId);
	}

	// 테마 생성
	@Override
	public boolean create(long userId, ThemeDto theme) {

		if (!checkUser(userId, theme.getGroupId()))
			throw new UserNotInGroupException(userId);

		int result = 0;
		// AOP를 이용해서 그룹 아이디가 존재하는것인지 체크 할 필요가 있을듯!

		// 해당 그룹의 마지막 테마번호 확인
		int lastId = this.readLastId(theme.getGroupId());

		// 테마 아이디 1 추가
		theme.setThemeId(lastId + 1);

		try {
			// 1. 테마 이름 정규식을 통해서 유효성 검사
			// 2. 테마 아이콘 역시 유효성 검사 필요
			result = dao.create(theme);
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}

		return result > 0;
	}

	@Override
	public List<ThemeDto> read(long userId, int groupId) {

		if (!checkUser(userId, groupId))
			throw new UserNotInGroupException(userId);

		return dao.read(groupId);
	}

	@Override
	public void update(long userId, int groupId, int themeId, ThemeDto theme) {

		if (!checkUser(userId, groupId))
			throw new UserNotInGroupException(userId);

		// 실수 방지
		theme.setGroupId(groupId);
		theme.setThemeId(themeId);

		int result = dao.update(theme);

		if (result < 1) {
			throw new ThemeNotFoundException(groupId, themeId);
		}

	}

	@Override
	public void delete(long userId, int groupId, int themeId) {

		if (!checkUser(userId, groupId))
			throw new UserNotInGroupException(userId);

		int result = dao.delete(groupId, themeId);

		if (result < 1) {
			throw new ThemeNotFoundException(groupId, themeId);
		}
	}

	private boolean checkUser(long userId, int groupId) {
		// 해당 그룹이 해당 유저가 속해있는지 유효성 체크하기
		List<GroupDto> list = service.readAll(userId);

		boolean isBelong = false;
		for (GroupDto dto : list) {
			if (dto.getGroupId() == groupId) {
				isBelong = true;
				break;
			}
		}
		return isBelong;
	}
}
