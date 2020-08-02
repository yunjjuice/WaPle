package com.ssafy.waple.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.waple.user.dto.UserDto;

@Mapper
public interface UserDao {
	// 회원 정보 추가
	int create(UserDto user) throws Exception;

	// 회원 정보 조회
	UserDto read(long userId) throws Exception;

	// 회원 정보 수정
	int update(UserDto user) throws Exception;

	// 회원 정보 삭제
	int delete(long userId) throws Exception;

	// 최근 접속 업데이트
	int updateTime(long userId) throws Exception;

	// 회원 리스트 조회
	List<UserDto> readAll();
}
