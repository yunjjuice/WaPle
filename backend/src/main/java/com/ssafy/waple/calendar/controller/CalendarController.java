package com.ssafy.waple.calendar.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.waple.calendar.dto.CalendarDto;
import com.ssafy.waple.calendar.service.CalendarService;
import com.ssafy.waple.common.PermissionCheck;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/calendars")
@RestController
@Api(value = "Calendar Controller", tags = "Calendar")
public class CalendarController {

	private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);

	@Autowired
	CalendarService service;

	@Autowired
	PermissionCheck permissionCheck;

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}"
		,produces = "application/json")
	@ApiOperation(value = "개인 스케줄 리스트", notes = "개인 스케쥴 전체를 보여주는 API", response = CalendarDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "Kakao API에서 제공한 회원 아이디", required = true,
			example = "1412733569"),
		@ApiImplicitParam(name = "token", value = "회원 토큰")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "스케줄 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 404, message = "스케줄 조회 실패")
	})
	private ResponseEntity<?> readAll(@PathVariable("userId")Long userId, @RequestHeader(value = "token")String token) {
		logger.debug("스케줄 호출");
		List<CalendarDto> result = service.readAll(token, userId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
