package com.ssafy.waple.review.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

import com.google.gson.Gson;
import com.ssafy.waple.bookmark.dto.SearchType;
import com.ssafy.waple.error.exception.IncorrectFormatException;
import com.ssafy.waple.review.dto.ReviewDto;
import com.ssafy.waple.review.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/reviews")
@RestController
@Api(value = "Review Controller", tags = "Review")
public class ReviewController {

	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	// 리뷰 수정 임시 테이블
	public static class UpdateData {

		@ApiModelProperty(value = "제목", example = "사장님이 쏜다")
		String title;

		@ApiModelProperty(value = "내용", example = "내 자식에게 밥을 준다는 마인드!")
		String content;

		@ApiModelProperty(value = "미디어", example = "hahaha.jpg")
		String media;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getMedia() {
			return media;
		}

		public void setMedia(String media) {
			this.media = media;
		}
	}

	@Autowired
	ReviewService service;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "리뷰 생성", notes = "그룹의 추억 쌓기", response = ReviewDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "회원 토큰"),
		@ApiImplicitParam(name = "review", value = "리뷰 내용", dataTypeClass = ReviewDto.class)
	})
	@ApiResponses({
		@ApiResponse(code = 201, message = "리뷰 생성 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "리뷰 생성 실패")
	})
	private ResponseEntity<?> create(@RequestBody ReviewDto review, @RequestHeader(value = "token")String token) {
		logger.debug("리뷰 생성 호출");

		service.create(token, review);

		return new ResponseEntity<>(review, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/places/{placeId}", produces = "application/json")
	@ApiOperation(value = "리뷰 조회", notes = "그룹 아이디 및 테마 아이디로 리뷰 조회", response = ReviewDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "회원 토큰"),
		@ApiImplicitParam(name = "searchType", value = "검색 데이터 내용", dataTypeClass = SearchType.class),
		@ApiImplicitParam(name = "placeId", value = "장소 아이디")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "리뷰 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "리뷰 조회 실패")
	})
	private ResponseEntity<?> read(@PathVariable("placeId")String placeId ,String searchType,
		@RequestHeader(value = "token")String token) {
		logger.debug("리뷰 조회 호출");
		SearchType type = new SearchType();

		try {
			logger.debug("쿼리스트링 파싱 시작");
			Gson gson = new Gson();
			String temp = URLDecoder.decode(searchType,"UTF-8");
			type = gson.fromJson(temp, SearchType.class);
		} catch (UnsupportedEncodingException e) {
			throw new IncorrectFormatException("URL 쿼리 인코딩을 확인해 주세요");
		}

		List<ReviewDto> result = service.read(token, type, placeId);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{reviewId}",produces = "application/json")
	@ApiOperation(value = "리뷰 상세 조회", notes = "리뷰 아이디로 리뷰 상세 조회", response = ReviewDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "회원 토큰"),
		@ApiImplicitParam(name = "reviewId", value = "리뷰 아이디")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "리뷰 상세 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "리뷰 상세 조회 실패")
	})
	private ResponseEntity<?> read(@PathVariable("reviewId")int reviewId, @RequestHeader(value = "token")String token) {
		logger.debug("리뷰 상세 조회 호출");
		ReviewDto review = service.read(token, reviewId);
		return new ResponseEntity<>(review, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{reviewId}",produces = "application/json")
	@ApiOperation(value = "리뷰 수정", notes = "리뷰 아이디로 리뷰 수정")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "회원 토큰"),
		@ApiImplicitParam(name = "reviewId", value = "리뷰 아이디"),
		@ApiImplicitParam(name = "review", value = "수정 할 리뷰 정보", dataTypeClass = UpdateData.class)
	})
	@ApiResponses({
		@ApiResponse(code = 201, message = "리뷰 수정 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "리뷰 수정 실패")
	})
	private ResponseEntity<?> update(@PathVariable("reviewId") int reviewId, @RequestBody UpdateData review,
		@RequestHeader(value = "token")String token) {
		logger.debug("리뷰 업데이트 호출");
		service.update(token, reviewId, review.title, review.content, review.media);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
