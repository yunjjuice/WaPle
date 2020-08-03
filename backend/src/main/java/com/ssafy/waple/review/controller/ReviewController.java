package com.ssafy.waple.review.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.waple.review.dto.ReviewDto;
import com.ssafy.waple.review.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/reviews")
@RestController
@Api(value = "Review Controller", tags = "Review")
public class ReviewController {

	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	@Autowired
	ReviewService service;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "리뷰 생성", notes = "그룹의 추억 쌓기", response = ReviewDto.class)
	@ApiImplicitParams({
		// @ApiImplicitParam(name = "token", value = "회원 토큰"),
		@ApiImplicitParam(name = "review", value = "리뷰 내용", dataTypeClass = ReviewDto.class)
	})
	@ApiResponses({
		@ApiResponse(code = 201, message = "리뷰 생성 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "리뷰 생성 실패")
	})
	private ResponseEntity<?> create(@RequestBody ReviewDto review) {
		logger.debug("리뷰 생성 호출");

		service.create(review);

		return new ResponseEntity<>(review, HttpStatus.CREATED);
	}
}
