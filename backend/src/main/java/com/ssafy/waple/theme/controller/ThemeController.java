package com.ssafy.waple.theme.controller;

import java.util.List;
import java.util.Map;

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

import com.ssafy.waple.theme.dto.ThemeGroupPlace;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.ssafy.waple.common.PermissionCheck;
import com.ssafy.waple.theme.dto.ThemeDto;
import com.ssafy.waple.theme.dto.ThemeGroupAll;
import com.ssafy.waple.theme.service.ThemeService;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/themes")
@Api(value = "테마 관리", tags = "Theme")
public class ThemeController {
	private static final Logger logger = LoggerFactory.getLogger(ThemeController.class);

	@Autowired
	ThemeService service;

	// 테마 추가
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "테마 생성", notes = "그룹에 테마 추가", response = ThemeDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "theme", value = "생성할 테마 정보", dataTypeClass = ThemeDto.class),
		@ApiImplicitParam(name = "token", value = "회원 토큰")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "테마 생성 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "테마 생성 실패")
	})
	private ResponseEntity<?> create(@RequestBody ThemeDto theme, @RequestHeader("token") String token) {
		logger.debug("테마 생성 호출");
		service.create(token, theme.getGroupId(), theme);
		return new ResponseEntity<>(theme, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{groupId}", produces = "application/json")
	@ApiOperation(value = "테마 조회", notes = "그룹의 테마 조회", response = ThemeDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "회원 토큰"),
		@ApiImplicitParam(name = "groupId", value = "그룹 아이디", example = "1")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "테마 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "테마 조회 실패")
	})
	private ResponseEntity<?> read(@PathVariable("groupId") int groupId,
		@RequestHeader(value = "token") String token) {
		logger.debug("테마 조회 호출");
		return new ResponseEntity<>(service.read(token, groupId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{groupId}/{themeId}", produces = "application/json")
	@ApiOperation(value = "테마 수정", notes = "테마의 이름 및 아이콘 수정", response = ThemeDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "회원 토큰"),
		@ApiImplicitParam(name = "theme", value = "수정 할 테마 정보", dataTypeClass = ThemeDto.class)
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "테마 수정 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "테마 수정 실패")
	})
	private ResponseEntity<?> update(@RequestBody ThemeDto theme, @RequestHeader(value = "token") String token) {
		logger.debug("테마 수정 호출");
		service.update(token, theme);
		return new ResponseEntity<>(theme, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{groupId}/{themeId}", produces = "application/json")
	@ApiOperation(value = "테마 삭제", notes = "해당 그룹의 테마를 삭제하는 기능 (삭제시 테마내 모든 북마크 삭제됨)",
		response = ThemeDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "groupId", value = "그룹 아이디", example = "1"),
		@ApiImplicitParam(name = "themeId", value = "테마 아이디", example = "1"),
		@ApiImplicitParam(name = "token", value = "회원 토큰")
	})
	@ApiResponses({
		@ApiResponse(code = 204, message = "테마 삭제 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "테마 삭제 실패")
	})
	private ResponseEntity<?> delete(@PathVariable("groupId") int groupId, @PathVariable("themeId") int themeId,
		@RequestHeader(value = "token") String token) {
		logger.debug("테마 삭제 호출");
		service.delete(token, groupId, themeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/{userId}", produces = "application/json")
	@ApiOperation(value = "테마 및 그룹 전체 조회", notes = "해당 유저의 그룹 및 모든 테마를 조회",
		response = ThemeGroupAll.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "유저 아이디", example = "1412733569"),
		@ApiImplicitParam(name = "token", value = "회원 토큰")
	})
	@ApiResponses({
		@ApiResponse(code = 204, message = "테마 및 그룹 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "테마 및 그룹 조회 실패")
	})
	private ResponseEntity<?> readAll(@PathVariable("userId") long userId,
		@RequestHeader(value = "token") String token) {
		logger.debug("테마 및 그룹 전체 조회 호출");
		List<ThemeGroupAll> result = service.readAll(token, userId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/place/{userId}/{placeId}", produces = "application/json")
	@ApiOperation(value = "해당 장소의 테마 및 그룹 전체 조회", notes = "해당 장소의 그룹 및 모든 테마를 조회",
		response = ThemeGroupPlace.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "유저 아이디", example = "1412733569"),
		@ApiImplicitParam(name = "placeId", value = "장소 아이디", example = "19781214"),
		@ApiImplicitParam(name = "token", value = "회원 토큰")
	})
	@ApiResponses({
		@ApiResponse(code = 204, message = "해당 장소의 테마 및 그룹 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "해당 장소의 테마 및 그룹 조회 실패")
	})
	private ResponseEntity<?> readAll(@PathVariable("placeId") String placeId, @PathVariable("userId") long userId,
		@RequestHeader(value = "token") String token) {
		logger.debug("해당 장소의 테마 및 그룹 전체 조회 호출");
		List<ThemeGroupPlace> result = service.readAll(token, userId, placeId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/place/{userId}", produces = "application/json")
	private ResponseEntity<?> readAllById(@PathVariable("userId") long userId,
		@RequestHeader(value = "token") String token) {
		logger.debug("해당 장소의 테마 및 그룹 전체 조회 호출");
		List<ThemeGroupPlace> result = service.readAllById(token, userId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
