package com.ssafy.waple.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.waple.error.exception.DuplicateKeyException;
import com.ssafy.waple.group.exception.GroupNotFoundException;
import com.ssafy.waple.notification.dao.NotificationDao;
import com.ssafy.waple.notification.dto.NotificationDto;
import com.ssafy.waple.notification.exception.DateFormatException;
import com.ssafy.waple.notification.exception.GroupIdInvaildException;

@Service
public class NotificationServiceImpl implements NotificationService {
	private static final String GROUP_ID_EMPTY_MSG = "Field 'GROUP_ID' doesn't have a default value";
	private static final String GROUP_FOREIGN_KEY_CONSTRAINT_MSG = "a foreign key constraint fails (`WAPLE`.`NOTIFICATIONS`, CONSTRAINT `FK_GROUPS_NOTIFICATIONS` FOREIGN KEY (`GROUP_ID`) REFERENCES `GROUPS` (`GROUP_ID`)";
	private static final String DATE_FORMAT_ERROR = "Incorrect date value";
	private static final String PRIMARY_KEY_CONSTRAINT_MSG = "for key 'PRIMARY'";
	private static final String PRIMARY_KEY_NULL_MSG = "'NOTIFICATION_ID' cannot be null";

	@Autowired
	NotificationDao dao;

	// 알림 생성
	@Override
	public void create(String token, NotificationDto dto) {
		try{
			// 생성 전 알림 아이디 조회 및 설정
			dao.create(dto);
		} catch (DataAccessException e) {
			if(e.getMessage().contains(PRIMARY_KEY_NULL_MSG)) {
				dao.createInit(dto);
				return;
			}
			if(e.getMessage().contains(GROUP_FOREIGN_KEY_CONSTRAINT_MSG)) {
				throw new GroupNotFoundException(dto.getGroupId());
			}
			if(e.getMessage().contains(GROUP_ID_EMPTY_MSG)) {
				throw new GroupIdInvaildException();
			}
			if(e.getMessage().contains(DATE_FORMAT_ERROR)) {
				throw new DateFormatException();
			}
			if(e.getMessage().contains(PRIMARY_KEY_CONSTRAINT_MSG)) {
				throw new DuplicateKeyException("Duplicate Notification Key Value");
			}
			throw e;
		}
	}

	// 알림 조회
	@Override
	public NotificationDto read(String token, int groupId, int notificationId) {
		// 유저가 그룹에 속해있는지 AOP 검사
		return dao.read(groupId, notificationId);
	}

}
