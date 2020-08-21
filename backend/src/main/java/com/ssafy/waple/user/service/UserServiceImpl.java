package com.ssafy.waple.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.waple.user.dao.UserDao;
import com.ssafy.waple.user.dto.UserDto;
import com.ssafy.waple.user.exception.UserNameNotFoundException;
import com.ssafy.waple.user.exception.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao dao;

	@Override
	public void create(UserDto user) {
		if (user.getName() == null || user.getName() == "") {
			throw new UserNameNotFoundException();
		}
		dao.create(user);
	}

	// 로그인
	@Override
	public void login(UserDto user) {
		dao.login(user);
	}

	@Override
	public UserDto read(String token, long userId) {
		return dao.read(userId);
	}

	@Override
	public void update(String token, UserDto user) {
		if (user.getName() == null || user.getName() == "") {
			throw new UserNameNotFoundException();
		}
		if (dao.update(user) < 1) {
			throw new UserNotFoundException(user.getUserId());
		}
	}

	@Override
	public void delete(String token, long userId) {
		dao.delete(userId);
	}

	@Override
	public List<UserDto> readAll(String token) {
		return dao.readAll();
	}
}
