package com.ssafy.waple.notification.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.waple.notification.dto.NotificationDto;
import com.ssafy.waple.notification.service.NotificationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/notifications")
@RestController
@Api(value = "알림 관리", tags = "Notification")
public class NotificationController {
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	NotificationService service;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "알림 생성", notes = "그룹 알림 추가")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "notification", value = "생성할 알림 정보"),
		@ApiImplicitParam(name = "token", value = "회원 토큰")
	})
	@ApiResponses({
		@ApiResponse(code = 201, message = "알림 생성 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "알림 생성 실패")
	})
	private ResponseEntity<?> create(@RequestBody NotificationDto notification,
		@RequestHeader(value = "token")String token) {
		logger.debug("알림 생성 호출");
		service.create(token, notification);
		return new ResponseEntity<>(notification, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{groupId}/{notificationId}", produces = "application/json")
	@ApiOperation(value = "알림 조회", notes = "그룹 알림 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "notificationId", value = "알림 아이디", example = "1"),
		@ApiImplicitParam(name = "groupId", value = "그룹 아이디", example = "1"),
		@ApiImplicitParam(name = "token", value = "회원 토큰")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "알림 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "알림 조회 실패")
	})
	private ResponseEntity<?> read(@PathVariable("groupId")int groupId,
		@PathVariable("notificationId")int notificationId, @RequestHeader(value = "token")String token) {
		logger.debug("알림 조회 호출");
		NotificationDto dto = service.read(token, groupId, notificationId);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}" ,produces = "application/json")
	@ApiOperation(value = "알림 리스트 조회", notes = "그룹 알림 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "Kakao API에서 제공한 회원 아이디", required = true,
			example = "1412733569"),
		@ApiImplicitParam(name = "token", value = "회원 토큰")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "알림 리스트 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "알림 리스트 조회 실패")
	})
	private ResponseEntity<?> readAll(@PathVariable("userId")Long userId, @RequestHeader(value = "token")String token) {
		logger.debug("알림 리스트 조회 호출");
		List<NotificationDto> result = service.readAll(token, userId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
