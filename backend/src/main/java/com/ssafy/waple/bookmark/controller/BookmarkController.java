package com.ssafy.waple.bookmark.controller;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.ssafy.waple.bookmark.dto.BookmarkDto;
import com.ssafy.waple.bookmark.dto.PlaceDto;
import com.ssafy.waple.bookmark.dto.SearchType;
import com.ssafy.waple.bookmark.service.BookmarkService;
import com.ssafy.waple.bookmark.service.PlaceService;
import com.ssafy.waple.common.PermissionCheck;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/bookmarks")
@Api(value = "북마크 관리", tags = "Bookmark")
public class BookmarkController {
	private static final Logger logger = LoggerFactory.getLogger(BookmarkController.class);

	@Autowired
	BookmarkService service;

	@Autowired
	PlaceService placeService;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "북마크 생성", notes = "북마크 생성하는 API")
	@ApiImplicitParam(name = "bookmark", value = "북마크 생성", required = true, dataTypeClass = BookmarkDto.class)
	@ApiResponses({
		@ApiResponse(code = 201, message = "북마크 생성 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "북마크 생성 실패")
	})

	private ResponseEntity<?> create(@RequestHeader(value = "token")String token, @RequestBody BookmarkDto bookmark) {
		logger.debug("북마크 생성 호출");
		service.create(token, bookmark);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "북마크 조회", notes = "회원이 속한 모든 그룹, 모든 테마 북마크 조회", response = BookmarkDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "searchType", value = "북마크 조회", required = true, dataTypeClass = SearchType.class),
		@ApiImplicitParam(name = "token", value = "회원 토큰")
	})
	@ApiResponses({
		@ApiResponse(code = 201, message = "북마크 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "북마크 조회 실패")
	})
	private ResponseEntity<?> read(@RequestHeader(value = "token")String token, String searchType) {
		logger.debug("북마크 조회 시작");
		// front search data 양식
		/*
		let searchData = {
			userId: 123,
			groups: [
				{ groupId: 123, themeId: 123 },
				{ groupId: 123, themeId: 456 },
				{ groupId: 123, themeId: 789 },
				{ groupId: 456, themeId: 123 },
				{ groupId: 456, themeId: 456 },
				{ groupId: 456, themeId: 789 },
        	],
        	limit: 10, offset: 0
      	}
      */

		// 검색 데이터 저장
		SearchType type = new SearchType();

		try {
			logger.debug("쿼리스트링 파싱 시작");
			Gson gson = new Gson();
			String temp = URLDecoder.decode(searchType, "UTF-8");
			type = gson.fromJson(temp, SearchType.class);
			logger.debug("쿼리스트링 파싱 종료");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<BookmarkDto> result = service.read(token, type);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{groupId}/{placeId}/{themeId}",
		produces = "application/json")
	@ApiOperation(value = "북마크 삭제", notes = "북마크 삭제 기능")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "groupId", value = "그룹 아이디", example = "1", required = true),
		@ApiImplicitParam(name = "placeId", value = "장소 아이디", example = "19781214", required = true),
		@ApiImplicitParam(name = "themeId", value = "테마 아이디", example = "1", required = true),
		@ApiImplicitParam(name = "token", value = "토큰", example = "das4dq-84AET68M1s", required = true)
	})
	@ApiResponses({
		@ApiResponse(code = 204, message = "북마크 삭제 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "북마크 삭제 실패")
	})
	private ResponseEntity<?> delete(@PathVariable("groupId") int groupId, @PathVariable("placeId") String placeId,
		@PathVariable("themeId") int themeId, @RequestHeader(value = "token") String token) {
		service.delete(token, themeId, groupId, placeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/{userId}/{limit}/{offset}", produces = "application/json")
	@ApiOperation(value = "북마크 조회", notes = "회원이 속한 모든 그룹, 모든 테마 북마크 조회", response = BookmarkDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "회원 토큰"),
		@ApiImplicitParam(name = "userId", value = "Kakao에서 제공하는 회원 아이디" , example = "1412733569"),
		@ApiImplicitParam(name = "limit", value = "페이지내 표시 할 북마크 리스트 수", example = "10"),
		@ApiImplicitParam(name = "offset", value = "페이지 위치", example = "1")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "북마크 전체 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "북마크 전체 조회 실패")
	})
	private ResponseEntity<?> readAll(@RequestHeader(value = "token")String token, @PathVariable("userId")long userId,
		@PathVariable("limit")int limit, @PathVariable("offset")int offset) {
		List<BookmarkDto> result = service.readAll(token, userId, limit, offset);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
