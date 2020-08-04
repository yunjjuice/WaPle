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
	private static final PermissionCheck permissionCheck = new PermissionCheck();

	@Autowired
	BookmarkService service;

	@Autowired
	PlaceService placeService;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "북마크 생성", notes = "북마크 생성하는 API")
	@ApiImplicitParam(name = "bookmark", value = "북마크 생성", required = true, dataTypeClass = BookmarkDto.class)
	@ApiResponses({
		@ApiResponse(code = 201, message = "북마크 생성 성공"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "북마크 생성 실패")
	})

	private ResponseEntity<?> create(@RequestBody BookmarkDto bookmark) {
		logger.debug("북마크 생성 호출");
		service.create(bookmark);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "북마크 조회", notes = "회원이 속한 모든 그룹, 모든 테마 북마크 조회", response = PlaceDto.class)
	@ApiImplicitParam(name = "searchType", value = "북마크 조회", required = true, dataTypeClass = SearchType.class)
	@ApiResponses({
		@ApiResponse(code = 201, message = "북마크 조회 성공"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "북마크 조회 실패")
	})
	private ResponseEntity<?> read(String searchType) {
		logger.debug("북마크 조회 시작");
		// front search data 양식
		/*
		let searchData = {
			userId: 123,
			groups: [
				{ groupId: 123, themeIds: [123, 456, 789]},
				{ groupId: 456, themeIds: [123, 456, 789]},
        	],
        	limit: 10, offset: 1
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

		List<BookmarkDto> result = service.read(type);
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
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "북마크 삭제 실패")
	})
	private ResponseEntity<?> delete(@PathVariable("groupId") int groupId, @PathVariable("placeId") String placeId,
		@PathVariable("themeId") int themeId, @RequestHeader(value = "token") String token) {
		long userId = permissionCheck.check(token).getUserId();
		service.delete(userId, themeId, groupId, placeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
