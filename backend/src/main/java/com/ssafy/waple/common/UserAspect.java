package com.ssafy.waple.common;

import java.util.List;

import com.ssafy.waple.error.exception.BusinessException;
import com.ssafy.waple.error.exception.ErrorCode;
import com.ssafy.waple.user.dto.UserDto;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.waple.group.dto.GroupDto;
import com.ssafy.waple.group.service.GroupService;
import com.ssafy.waple.user.exception.UserOwnerException;

@Aspect
@Component
public class UserAspect {

	@Autowired
	GroupService groupService;

	@Autowired
	PermissionCheck check;

	private static final Logger logger = LoggerFactory.getLogger(UserAspect.class);

	@Pointcut("execution(void com.ssafy.waple.user.service.*.create(..)))")
	private void signUp() {}


	@Pointcut("execution(void com.ssafy.waple.user.service.*.delete(..))")
	private void ownerCheck() {}

	@Pointcut("execution(java.util.List com.ssafy.waple.user.service.*.*(..))")
	private void isAdmin() {}

	@AfterReturning("signUp()")
	public void userChecking(JoinPoint point) {
		logger.debug("회원가입으로 인한 그룹 생성 호출");
		Object[] parameterValuse = point.getArgs();

		UserDto user = (UserDto)parameterValuse[0];

		// 그룹 생성
		GroupDto group = new GroupDto();
		group.setUserId(user.getUserId());
		group.setName(user.getName());
		groupService.create(group);
	}

	@Before("ownerCheck()")
	public void userGrouupChecking(JoinPoint point) {
		logger.debug("그룹장인지 조회");
		Object[] parameterValuse = point.getArgs();
		long userId = (long) parameterValuse[1];
		// 그룹장인지 체크
		List<GroupDto> list = groupService.readAll(userId);
		for (GroupDto dto : list) {
			if (groupService.isOwner(dto.getGroupId(), userId)) {
				throw new UserOwnerException(dto.getGroupId());
			}
		}
		// 그룹장이 아닐 경우 그룹에서 탈퇴
		for (GroupDto dto : list) {
			groupService.delete(dto.getGroupId(), dto.getUserId());
		}
	}

	@Before("isAdmin()")
	private void isManager(JoinPoint point) {
		logger.debug("관리자인지 조회");
		Object[] parameterValue = point.getArgs();
		String token = (String)parameterValue[0];
		if(!check.check(token).isManagerFlag()) {
			throw new BusinessException("Is not manager", ErrorCode.HANDLE_ACCESS_DENIED);
		}
	}
}
