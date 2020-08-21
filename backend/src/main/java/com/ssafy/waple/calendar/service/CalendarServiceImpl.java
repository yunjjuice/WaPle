package com.ssafy.waple.calendar.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.waple.calendar.dao.CalendarDao;
import com.ssafy.waple.calendar.dto.CalendarDto;
import com.ssafy.waple.group.dto.GroupDto;
import com.ssafy.waple.group.service.GroupService;

@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	CalendarDao dao;

	// 스케줄 조회
	@Override
	public List<CalendarDto> readAll(String token, long userId) {
		return dao.readAll(userId);
	}
}
