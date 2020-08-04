package com.ssafy.waple.group.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.ssafy.waple.group.dto.GroupDto;
import com.ssafy.waple.group.dto.GroupMemberDto;
import com.ssafy.waple.group.service.GroupService;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(value = "그룹 관리", tags = "Group")
@RestController
@RequestMapping("/groups")
public class GroupController {
	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	GroupService service;

	@GetMapping(value = "/of/{userId}")
	@ApiOperation(value = "특정 유저의 그룹 목록 조회", notes = "유저가 가입한 그룹의 목록을 반환한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "그룹 목록 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹 목록 조회 실패")
	})
	private ResponseEntity<List<GroupDto>> readAll(
		@ApiParam(value = "유저 id", required = true, example = "1412733569") @PathVariable long userId) {
		logger.debug("readAll 호출");
		return new ResponseEntity<>(service.readAll(userId), HttpStatus.OK);
	}

	@GetMapping(value = "/{groupId}")
	@ApiOperation(value = "그룹의 멤버 목록 조회", notes = "해당 그룹의 멤버 목록을 반환한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "멤버 목록 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "멤버 목록 조회 실패")
	})
	private ResponseEntity<List<GroupMemberDto>> readGroupMembers(
		@ApiParam(value = "그룹 id", required = true, example = "1") @PathVariable int groupId) {
		logger.debug("readGroupMembers 호출");
		return new ResponseEntity<>(service.readGroupMembers(groupId), HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "그룹 생성", notes = "새로운 그룹을 생성한다.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "그룹 생성 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹 생성 실패")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(
			value = "생성할 그룹 정보",
			required = true,
			name = "group",
			dataTypeClass = GroupDtoExample.class)
	})
	private ResponseEntity<?> create(@RequestBody GroupDto group) {
		logger.debug("create 호출");
		service.create(group);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping(value = "/member")
	@ApiOperation(value = "그룹에 멤버 추가", notes = "그룹에 멤버를 추가한다.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "그룹에 멤버 추가 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹에 멤버 추가 실패")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(
			value = "추가할 멤버 정보",
			required = true,
			name = "member",
			dataTypeClass = GroupMemberDtoExample.class)
	})
	private ResponseEntity<?> createMember(@RequestBody GroupDto member) {
		logger.debug("createMember 호출");
		service.createMember(member);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping
	@ApiOperation(value = "그룹 수정", notes = "그룹장, 그룹 이름을 수정한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "그룹 수정 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹 수정 실패")
	})
	private ResponseEntity<GroupDto> update(
		@ApiParam(value = "수정할 그룹 정보", required = true) @RequestBody GroupDto group) {
		logger.debug("update 호출");
		service.update(group);
		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	@DeleteMapping("/{groupId}/{userId}")
	@ApiOperation(value = "그룹 탈퇴 및 삭제", notes = "유저가 그룹장이고 그룹에 다른 멤버가 없을 경우 그룹을 삭제하고, 아닐 경우 그룹에서 탈퇴한다.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "그룹 삭제 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹 삭제 실패"),
		@ApiResponse(code = 409, message = "(그룹 삭제 only) 그룹에 멤버가 있습니다")
	})
	private ResponseEntity<?> delete(
		@ApiParam(value = "탈퇴 혹은 삭제할 그룹 id", required = true, example = "1") @PathVariable int groupId,
		@ApiParam(value = "유저 id", required = true, example = "1412733569") @PathVariable long userId) {
		service.delete(groupId, userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	public class GroupDtoExample {
		@ApiModelProperty(value = "그룹장 Id", example = "1412733569")
		private long userId;

		@ApiModelProperty(value = "그룹 이름", example = "나")
		private String name;

		public long getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public class GroupMemberDtoExample {
		@ApiModelProperty(value = "그룹 Id", example = "1")
		private int groupId;

		@ApiModelProperty(value = "멤버 Id", example = "1412733569")
		private long userId;

		public int getGroupId() {
			return groupId;
		}

		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}
	}
}
