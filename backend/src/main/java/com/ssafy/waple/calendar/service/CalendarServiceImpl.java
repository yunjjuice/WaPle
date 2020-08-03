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

	public class DayPoint {
		private Date dayFirst;
		private Date dayLast;

		public Date getDayFirst() {
			return dayFirst;
		}

		public void setDayFirst(Date dayFirst) {
			this.dayFirst = dayFirst;
		}

		public Date getDayLast() {
			return dayLast;
		}

		public void setDayLast(Date dayLast) {
			this.dayLast = dayLast;
		}
	}

	@Autowired
	CalendarDao dao;

	// 스케줄 조회
	@Override
	public List<CalendarDto> readAll(long userId, String unit, int year, int month, int day) {


		List<CalendarDto> result = new ArrayList<>();

		DayPoint dayPoint = new DayPoint();
		// 1. 일 조회
		if(unit.equals("DAY")) {
			dayPoint = getToday(year, month, day);
			result = dao.readAll(userId, dayPoint.getDayFirst(), dayPoint.getDayLast());
			// 추후에 논의하여 장소 이름 등 추가
		} else {
			// 월 조회
			if(unit.equals("MONTH")) {
				dayPoint = getLastDay(year,month);
				result = dao.readAll(userId, dayPoint.getDayFirst(), dayPoint.getDayLast());
				// 주 조회
			} else {
				dayPoint = getWeekend(year, month, day);
				result = dao.readAll(userId, dayPoint.getDayFirst(), dayPoint.getDayLast());
			}
		}

		return result;
	}

	private DayPoint getLastDay(int year, int month) {

		DayPoint dayPoint = new DayPoint();
		Calendar calendar = Calendar.getInstance(Locale.KOREA);

		// 년, 월 설정
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);

		// 해당 연월의 마지막날
		int last = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		// 월초 설정
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		dayPoint.setDayFirst(calendar.getTime());

		// 월말 설정
		calendar.set(Calendar.DATE, last);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		dayPoint.setDayLast(calendar.getTime());

		return dayPoint;
	}

	private DayPoint getToday(int year, int month, int day) {

		DayPoint dayPoint = new DayPoint();

		// 현재 시간
		Calendar calendar = Calendar.getInstance(Locale.KOREA);

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE, day);

		// 일 시작
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		dayPoint.setDayFirst(calendar.getTime());

		// 일 끝
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		dayPoint.setDayLast(calendar.getTime());

		return dayPoint;
	}

	private DayPoint getWeekend(int year, int month, int day) {

		// 현재 시간
		Calendar calendar = Calendar.getInstance(Locale.KOREA);

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE, day);
		Date date = calendar.getTime();

		// 금주 첫날 마지막날 구하
		DayPoint dayPoint = new DayPoint();

		Calendar cal = Calendar.getInstance(Locale.KOREA);

		// 주초(월)
		cal.setTime(date);
		cal.add(Calendar.DATE, 1 - cal.get(Calendar.DAY_OF_WEEK));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		dayPoint.setDayFirst(cal.getTime());

		// 주말(토)
		cal.setTime(date);
		cal.add(Calendar.DATE, 7 - cal.get(Calendar.DAY_OF_WEEK));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);

		dayPoint.setDayLast(cal.getTime());

		return dayPoint;
	}
}
