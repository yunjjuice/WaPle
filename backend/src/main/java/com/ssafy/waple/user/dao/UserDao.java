package com.ssafy.waple.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.waple.user.dto.UserDto;

@Mapper
public interface UserDao {
	// 회원 정보 추가
	int create(UserDto user);

	// 로그인
	int login(UserDto user);

	// 회원 정보 조회
	UserDto read(long userId);

	// 회원 정보 수정
	int update(UserDto user);

	// 회원 정보 삭제
	int delete(long userId);

	// 회원 리스트 조회
	List<UserDto> readAll();
}
