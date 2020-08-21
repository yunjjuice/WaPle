package com.ssafy.waple.calendar.service;

import java.util.List;

import com.ssafy.waple.calendar.dto.CalendarDto;

public interface CalendarService {

	// 스케줄 조회
	List<CalendarDto> readAll(String token, long userId);
}
