package com.ssafy.waple.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ssafy.waple.group.dto.GroupDto;
import com.ssafy.waple.group.service.GroupService;
import com.ssafy.waple.user.dao.UserDao;
import com.ssafy.waple.user.dto.UserDto;
import com.ssafy.waple.user.exception.UserNameNotFoundException;
import com.ssafy.waple.user.exception.UserNotFoundException;
import com.ssafy.waple.user.exception.UserOwnerException;

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
		UserDto user = dao.read(userId);
		if(user == null) {
			throw new UserNotFoundException(userId);
		}
		return user;
	}

	@Override
	public void update(String token, UserDto user) {
		if (user.getName() == null || user.getName() == "") {
			throw new UserNameNotFoundException();
		}
		if(dao.update(user) < 1) {
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
