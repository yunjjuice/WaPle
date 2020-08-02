package com.ssafy.waple.user.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.ssafy.waple.common.PermissionCheck;
import com.ssafy.waple.user.dto.UserDto;
import com.ssafy.waple.user.service.UserService;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/users")
@Api(value = "User Controller", tags = "User")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final PermissionCheck permissionCheck = new PermissionCheck();
	
	@Autowired
	UserService service;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "로그인 또는 회원가입", notes = "카카오 API 통해 받은 토큰을 기반으로 신규회원이라면 DB에 저장하는 메서드",
		response = UserDto.class)
	@ApiImplicitParam(name = "accessToken", value = "Kakao API를 통해서 받는 임의의 토큰 값", required = true,
		dataTypeClass = AccessToken.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "로그인 또는 회원가입 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 404, message = "로그인 또는 회원가입 실패")
	})
	private ResponseEntity<?> create(@RequestBody String accessToken) {
		logger.debug("로그인 또는 회원가입 호출");
		Gson gson = new Gson();
		Response response = gson.fromJson(accessToken, Response.class);
		Response userInfo = this.getUserInfo(response.accessToken);

		long userId = Integer.parseInt(userInfo.id);
		String name = userInfo.data.get("nickname");
		boolean isAdmin = false;

		// 유저 정보 조회
		UserDto user = service.read(userId);

		// Access 토큰이 넘어온건 카카오 로그인이 정상적으로 처리되었다는 것을 의미한다고 생각했다.
		Boolean success = false;

		// DB 조회 후 없으면 추가 후 개인 그룹 생성 있으면 최근 접속 일자 업데이트
		if (user == null) {
			success = service.create(new UserDto(userId, name));
		} else {
			isAdmin = user.isManagerFlag();
			success = service.updateTime(userId);
		}

		String token = this.getToken(userId, name, isAdmin);

		if (success) {
			return new ResponseEntity<>(token, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}", produces = "application/json")
	@ApiOperation(value = "회원 정보 조회", notes = "회원 아이디를 기반으로 회원 정보 조회", response = UserDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "Kakao API에서 제공한 회원 아이디", required = true,
			example = "1412733569"),
		@ApiImplicitParam(name = "token", value = "회원 토큰", required = true)
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "회원정보 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "회원정보 조회 실패")
	})
	private ResponseEntity<?> read(@PathVariable("userId") Long userId, @RequestHeader(value = "token") String token) {
		logger.debug("회원정보 조회 호출");
		if (userId != permissionCheck.check(token).getUserId()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		UserDto user = service.read(userId);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{userId}", produces = "application/json")
	@ApiOperation(value = "회원 정보 수정", notes = "회원 아이디를 기반으로 회원 정보 수정 (닉네임만 가능)",
		response = UserDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "회원 토큰", required = true),
		@ApiImplicitParam(name = "user", value = "수정 할 유저 닉네임", required = true, dataTypeClass = UserDto.class),
		@ApiImplicitParam(name = "userId", value = "회원 아이디", required = true, example = "1412733569")
	})
	@ApiResponses({
		@ApiResponse(code = 201, message = "회원정보 수정 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "회원정보 수정 실패")
	})
	private ResponseEntity<?> update(@PathVariable("userId") long userId, @RequestHeader(value = "token") String token,
		@RequestBody UserDto user) {
		logger.debug("회원정보 업데이트 호출");
		if (userId != permissionCheck.check(token).getUserId()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Boolean success = service.update(user);
		if (success) {
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{userId}/{refreshToken}", produces = "application/json")
	@ApiOperation(value = "회원 정보 삭제", notes = "회원 아이디를 기반으로 DB에서 삭제")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "Kakao에서 제공해준 회원 아이디", required = true,
			example = "1412733569"),
		@ApiImplicitParam(name = "token", value = "회원 토큰", required = true),
		@ApiImplicitParam(name = "refreshToken", value = "Kakao API에서 제공한 리프레시 토큰", required = true,
			dataTypeClass = RefreshToken.class)
	})
	@ApiResponses({
		@ApiResponse(code = 204, message = "회원정보 삭제 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "회원정보 삭제 실패")
	})
	private ResponseEntity<?> delete(@PathVariable("userId") long userId, @RequestHeader(value = "token") String token,
		@PathVariable("refreshToken") String refreshToken) {
		logger.debug("회원정보 삭제 호출");

		long userTempId = permissionCheck.check(token).getUserId();
		if (userId != userTempId) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		String accessToken = this.getAccessToken(refreshToken);

		if (userId != this.getUnlinkId(accessToken)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Boolean success = service.delete(userId);

		if (success) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "회원리스트 조회", notes = "회원리스트 조회 (프론트에서 관리자 권한 체크함)", response = UserDto.class)
	@ApiImplicitParam(name = "token", value = "회원 토큰", required = true)
	@ApiResponses({
		@ApiResponse(code = 200, message = "회원 리스트 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "회원 리스트 조회 실패")
	})
	private ResponseEntity<?> readAll(@RequestHeader(value = "token") String token) {
		if (permissionCheck.check(token).isManagerFlag()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		List<UserDto> userList = service.readAll();
		if (userList.size() != 0) {
			return new ResponseEntity<>(userList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/logout", produces = "application/json")
	@ApiOperation(value = "로그아웃", notes = "Refresh 토큰을 기반으로 Kakao에 로그아웃 요청", response = Long.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "refreshToken", value = "Kakao API에서 제공한 리프레시 토큰", required = true,
			dataTypeClass = RefreshToken.class),
		@ApiImplicitParam(name = "token", value = "회원 토큰", required = true)
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "로그아웃 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "로그아웃 실패")
	})
	private ResponseEntity<?> logOut(@RequestBody String refreshToken, @RequestHeader(value = "token") String token) {
		logger.debug("로그아웃 호출");
		long userId = permissionCheck.check(token).getUserId();
		Boolean success = false;
		Gson gson = new Gson();
		Response response = gson.fromJson(refreshToken, Response.class);
		System.out.println("RefreshToken: " + response.refreshToken);

		// Refresh Token으로 Access Token 받기
		String accessToken = this.getAccessToken(response.refreshToken);
		logger.debug("AccessToken is " + accessToken);

		String reqURL = "https://kapi.kakao.com/v1/user/logout";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			// Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			String temp = this.getResponseBody(conn);

			gson = new Gson();
			response = gson.fromJson(temp, Response.class);

			userId = Integer.parseInt(response.id);
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		String tempToken = this.getToken(userId, "", false);
		if (success) {
			return new ResponseEntity<>(tempToken, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	private Response getUserInfo(String accessToken) {
		logger.debug("유저정보 요청 호출");
		// Response Type 선언 (유저 정보 여러개 필요)
		Response response = new Response();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");

			// Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			String result = this.getResponseBody(conn);

			Gson gson = new Gson();
			response = gson.fromJson(result, Response.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private String getAccessToken(String refreshToken) {
		logger.debug("AccessToken 호출");
		final String apiKey = "ace676611788b2cfc7cf64a57f83729b";
		String accessToken = "";

		String reqURL = "https://kauth.kakao.com/oauth/token";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			// Post 보내기 위한 설정
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			// Header에 포함될 내용
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			// POST 요청 시 필요한 파라메터 데이터
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();

			sb.append("grant_type=refresh_token&");
			sb.append("client_id=").append(apiKey);
			sb.append("&refresh_token=").append(refreshToken);

			bw.write(sb.toString());
			bw.flush();

			String result = this.getResponseBody(conn);

			Gson gson = new Gson();
			Response response = gson.fromJson(result, Response.class);
			accessToken = response.accessToken;
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	private long getUnlinkId(String accessToken) {
		logger.debug("카카오 연결 끊기 시작");
		Response response = new Response();
		String reqURL = "https://kapi.kakao.com/v1/user/unlink";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			// Post 보내기 위한 설정
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			// Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			String result = this.getResponseBody(conn);

			Gson gson = new Gson();
			response = gson.fromJson(result, Response.class);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return Long.valueOf(response.id);
	}

	private String getResponseBody(HttpURLConnection conn) throws IOException {
		logger.debug("ResponseBody 요청 호출");
		int responseCode = conn.getResponseCode();
		System.out.println("responseCode : " + responseCode);

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String line = "";
		String result = "";

		while ((line = br.readLine()) != null) {
			result += line;
		}
		System.out.println("response body : " + result);
		br.close();
		return result;
	}

	private String getToken(long userId, String name, boolean isAdmin) throws JWTCreationException {
		Algorithm algorithm = Algorithm.HMAC256("waple_project");
		String token = JWT.create().withClaim("User_ID", userId).withClaim("name", name)
			.withClaim("admin", isAdmin ? "true" : "false").sign(algorithm);
		return token;
	}

	// Response Body에서 받은 JSON 값을 Java Object로 파싱 하기 만든 객체
	class Response {
		@SerializedName("access_token")
		String accessToken;

		@SerializedName("refresh_token")
		String refreshToken;

		@SerializedName("id")
		String id;

		@SerializedName("properties")
		Map<String, String> data = new HashMap<>();
	}

	// 임시 Token 클래스
	public class RefreshToken {
		@ApiModelProperty(value = "리프레시 토큰값", notes = "Kakao API를 통해서 받는 토큰 값",
			example = "A12DIRKJ115-123ag-123A3LMJIK123")
		String refreshToken;

		public String getToken() {
			return refreshToken;
		}

		public void setToken(String token) {
			this.refreshToken = token;
		}
	}

	public class AccessToken {
		@ApiModelProperty(value = "액세스 토큰", notes = "Kakao API에서 제공하는 Access Token",
			example = "A12DIRKJ115-123ag-123A3LMJIK123")
		String accessToken;

		public String getAccessToken() {
			return accessToken;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}
	}
}
