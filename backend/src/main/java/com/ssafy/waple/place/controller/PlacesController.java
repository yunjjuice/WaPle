package com.ssafy.waple.place.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.ssafy.waple.bookmark.dto.PlaceDto;
import com.ssafy.waple.bookmark.service.PlaceService;
import com.ssafy.waple.user.controller.UserController;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/places")
@Api(value = "Places Controller", tags = "Place")
public class PlacesController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	PlaceService service;

	@RequestMapping(method = RequestMethod.GET, value = "/{placeId}", produces = "application/json")
	@ApiOperation(value = "장소 상세 조회", notes = "장소아이디 기반으로 상세 조회", response = PlaceDto.class)
	@ApiImplicitParam(name = "placeId", value = "장소 아이디", required = true, example = "19781214")
	@ApiResponses({
		@ApiResponse(code = 200, message = "장소 상세 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 404, message = "장소 장소 조회 실패")
	})
	private ResponseEntity<?> read(@PathVariable("placeId") String placeId) {
		logger.debug("장소 상세 조회 호출");
		PlaceDto place = service.read(placeId);

		if (place != null) {
			return new ResponseEntity<>(place, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
