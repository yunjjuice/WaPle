package com.ssafy.waple.common;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.waple.bookmark.dto.BookmarkDto;
import com.ssafy.waple.bookmark.dto.PlaceDto;
import com.ssafy.waple.bookmark.service.PlaceService;
import com.ssafy.waple.group.service.GroupService;
import com.ssafy.waple.user.service.UserService;

@Aspect
@Component
public class WapleAspect {

	private static final Logger logger = LoggerFactory.getLogger(WapleAspect.class);

	@Autowired
	PermissionCheck permissionCheck;

	@Autowired
	GroupService groupService;

	@Autowired
	UserService userService;

	@Autowired
	PlaceService placeService;

	@Pointcut("execution(void com.ssafy.waple.bookmark.service.*.*(*,*))")
	private void placeCheck() {}

	@Pointcut("execution(* com.ssafy.waple.theme.service.*.*(..)))")
	private void themeCheckUser() {}


	@Before("placeCheck()")
	private void placeChecking(JoinPoint point) {
		logger.debug("해당 장소가 디비에 있는지 체크 시작");
		Object[] parameterValues = point.getArgs();

		BookmarkDto dto = (BookmarkDto) parameterValues[1];
		PlaceDto placeDto = new PlaceDto(dto.getPlaceId(), dto.getName(), dto.getAddress(), dto.getLng(), dto.getLat(),
			dto.getUrl());
		placeService.create(placeDto);

	}



	// 해당 그룹에 유저가 있나요 체크 추가시 => || 포인트컷 메서드 네임 ex(" themeCheckUser() || hi()")
	@Before("themeCheckUser()")
	public void beforeChecking(JoinPoint point) {
		logger.debug("해당 그룹의 유저 유효성 체크 시작");
		Object[] parameterValues = point.getArgs();

		// ServiceImpl에서 첫번째 파라메터 값 (반드시 token 인지 체크)
		// ServiceImpl에서 두번째 파라메터 값 (반드시 groupId 인지 체크)
		// String token = (String)parameterValues[0];
		// int groupId = (Integer)parameterValues[1];
		//
		// boolean isTrue = false;
		//
		// long userId = check.check(token).getUserId();
		// List<GroupDto> list = groupService.readAll(userId);
		//
		// for (GroupDto dto : list) {
		// 	if (dto.getGroupId() == groupId) {
		// 		isTrue = true;
		// 		break;
		// 	}
		// }
		// if(!isTrue) {
		// 	throw new UserNotInGroupException(userId);
		// } else {
		// 	logger.debug("해당 그룹의 유저의 유효성 체크 완료");
		// }
	}

}
