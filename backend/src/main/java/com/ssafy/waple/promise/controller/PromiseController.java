package com.ssafy.waple.promise.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.ssafy.waple.promise.dto.PromiseDto;
import com.ssafy.waple.promise.service.PromiseService;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(value = "약속 관리", tags = "Promise")
@RestController
@RequestMapping("/promises")
public class PromiseController {
	private static final Logger logger = LoggerFactory.getLogger(PromiseController.class);

	@Autowired
	PromiseService service;

	@PostMapping
	@ApiOperation(value = "약속 생성", notes = "새로운 약속을 생성한다.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "약속 생성 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "약속 생성 실패")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(
			value = "생성할 약속 정보",
			required = true,
			name = "promise",
			dataTypeClass = PromiseRequest.class)
	})
	private ResponseEntity<?> create(@RequestBody PromiseDto promise) {
		logger.debug("create 호출");
		service.create(promise);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	public class PromiseRequest {
		@ApiModelProperty(value = "그룹 Id", example = "1")
		private int groupId;

		@ApiModelProperty(value = "약속 이름", example = "정기 방탈출 모임")
		private String title;

		@ApiModelProperty(value = "약속 날짜", example = "2020-07-31T18:00")
		private Date promiseDate;

		public int getGroupId() {
			return groupId;
		}

		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Date getPromiseDate() {
			return promiseDate;
		}

		public void setPromiseDate(Date promiseDate) {
			this.promiseDate = promiseDate;
		}
	}
}
