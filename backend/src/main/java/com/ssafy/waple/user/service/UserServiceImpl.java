package com.ssafy.waple.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.waple.group.dto.GroupDto;
import com.ssafy.waple.group.service.GroupService;
import com.ssafy.waple.user.dao.UserDao;
import com.ssafy.waple.user.dto.UserDto;
import com.ssafy.waple.user.exception.UserNameNotFoundException;
import com.ssafy.waple.user.exception.UserOwnerException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao dao;

	@Autowired
	GroupService service;

	@Override
	public Boolean create(UserDto user) {
		int result = 0;

		try {
			if (user.getName() == null || user.getName() == "") {
				throw new UserNameNotFoundException();
			}
			result = dao.create(user);
			// 그룹 생성
			GroupDto dto = new GroupDto();
			dto.setUserId(user.getUserId());
			dto.setName(user.getName());
			service.create(dto);
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		return result > 0;
	}

	@Override
	public UserDto read(long userId) {
		try {
			return dao.read(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean update(UserDto user) {
		int result = 0;
		if (user.getName() == null || user.getName() == "") {
			throw new UserNameNotFoundException();
		}
		try {
			result = dao.update(user);
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		return result > 0;
	}

	@Override
	public Boolean delete(long userId) {
		int result = 0;

		// 그룹장인지 체크
		List<GroupDto> list = service.readAll(userId);
		for (GroupDto dto : list) {
			if (service.isOwner(dto.getGroupId(), userId)) {
				throw new UserOwnerException(dto.getGroupId());
			}
		}

		try {
			result = dao.delete(userId);
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		return result > 0;
	}

	@Override
	public Boolean updateTime(long userId) {
		int result = 0;
		try {
			result = dao.updateTime(userId);
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		return result > 0;
	}

	@Override
	public List<UserDto> readAll() {
		return dao.readAll();
	}
}
