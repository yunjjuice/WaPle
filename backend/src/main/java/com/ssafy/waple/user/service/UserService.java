package com.ssafy.waple.user.service;

import java.util.List;

import com.ssafy.waple.user.dto.UserDto;

public interface UserService {
	// 회원 정보 추가 (회원 가입)
	void create(UserDto user);

	// 로그인
	void login(UserDto user);

	// 회원 정보 조회
	UserDto read(String token, long userId);

	// 회원 정보 수정
	void update(String token, UserDto user);

	// 회원 정보 삭제 (회원 탈퇴)
	void delete(String token, long userId);

	// 회원 리스트 조회
	List<UserDto> readAll(String token);
}
