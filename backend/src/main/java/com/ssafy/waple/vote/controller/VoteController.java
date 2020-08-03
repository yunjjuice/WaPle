package com.ssafy.waple.vote.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.ssafy.waple.common.PermissionCheck;
import com.ssafy.waple.group.controller.GroupController;
import com.ssafy.waple.vote.dto.PromisePlaceDto;
import com.ssafy.waple.vote.dto.PromisePlaceResponseDto;
import com.ssafy.waple.vote.service.VoteService;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(value = "투표 관리", tags = "Vote")
@RestController
@RequestMapping("/votes")
public class VoteController {
	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
	private static final PermissionCheck permissionCheck = new PermissionCheck();

	@Autowired
	VoteService service;

	@PostMapping
	@ApiOperation(value = "투표 장소 추가", notes = "약속에 투표를 할 후보 장소를 추가한다.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "투표 장소 추가 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "투표 장소 추가 실패")
	})
	private ResponseEntity<?> create(@RequestBody PromisePlaceDto promisePlace) {
		logger.debug("create 호출");
		service.create(promisePlace);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(value = "/{groupId}/{promiseId}")
	@ApiOperation(value = "투표 장소 목록 조회", notes = "해당 약속의 투표를 할 후보 장소 목록을 반환한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "그룹 목록 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹 목록 조회 실패")
	})
	private ResponseEntity<List<PromisePlaceResponseDto>> readAll(
		@ApiParam(value = "그룹 Id", required = true, example = "1") @PathVariable int groupId,
		@ApiParam(value = "약속 Id", required = true, example = "1") @PathVariable int promiseId,
		@RequestHeader(value = "token") String token) {
		logger.debug("readAll 호출");
		long userId = permissionCheck.check(token).getUserId();
		return new ResponseEntity<>(service.readAll(groupId, promiseId, userId), HttpStatus.OK);
	}
}
