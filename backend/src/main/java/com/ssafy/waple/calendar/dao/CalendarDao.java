package com.ssafy.waple.calendar.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.waple.calendar.dto.CalendarDto;
import com.ssafy.waple.group.dto.GroupDto;

@Mapper
public interface CalendarDao {

	// 스케줄 조회
	List<CalendarDto> readAll(long userId);
}
