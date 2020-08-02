package com.ssafy.waple.user.service;

import java.util.List;

import com.ssafy.waple.user.dto.UserDto;

public interface UserService {
	// 회원 정보 추가 (회원 가입)
	Boolean create(UserDto user);

	// 회원 정보 조회
	UserDto read(long userId);

	// 회원 정보 수정
	Boolean update(UserDto user);

	// 회원 정보 삭제 (회원 탈퇴)
	Boolean delete(long userId);

	// 최근 접속 업데이트
	Boolean updateTime(long userId);

	// 회원 리스트 조회
	List<UserDto> readAll();
}
